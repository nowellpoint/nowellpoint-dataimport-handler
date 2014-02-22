package com.nowellpoint.handler.dataimport;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;

import com.nowellpoint.handler.dataimport.config.Entities;
import com.nowellpoint.handler.dataimport.config.Entity;
import com.nowellpoint.handler.dataimport.config.EntityMapping;
import com.nowellpoint.handler.dataimport.exception.DataImportException;
import com.nowellpoint.handler.dataimport.mysql.TableImporter;
import com.nowellpoint.handler.dataimport.sforce.DataExporter;
import com.nowellpoint.handler.dataimport.task.ImportTask;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

public class DataImporter {
	
	private Long organizationId;
	private Long userId;
	private String mappingFile;
	private PartnerConnection connection;
	private String outputDirectory;
	private EntityManagerFactory emf;
	
	public DataImporter(String mappingFile, Long organizationId, Long userId, PartnerConnection connection, EntityManagerFactory emf, String outputDirectory) {
		this.mappingFile = mappingFile;
		this.organizationId = organizationId;
		this.userId = userId;
		this.connection = connection;
		this.emf = emf;
		this.outputDirectory = outputDirectory;
	}

	public void runFullImport() {
		ObjectMapper om = new ObjectMapper();
		try {
			EntityMapping mapping = om.readValue(mappingFile, EntityMapping.class);
			for (Entities entities : mapping.getEntities()) {
				String type = entities.getType();
				Entity entity = entities.getEntity();	
				
				createDataFile(type, entity);
				
				if (entity.getPreImport() != null && entity.getPreImport().trim().length() > 0) {
					if (Class.forName(entity.getPreImport()).getSuperclass().isAssignableFrom(ImportTask.class)) {
						ImportTask task = (ImportTask) Class.forName(entity.getPostImport()).newInstance();
						task.run(emf, connection, entity);
					}
				}
				
				if (entity.getDefaultValues() == null) {
					entity.setDefaultValues(new HashMap<String, String>());
				}
				
				entity.getDefaultValues().put("CREATED_BY_ID", String.valueOf(userId));
				entity.getDefaultValues().put("LAST_MODIFIED_BY_ID", String.valueOf(userId));
				entity.getDefaultValues().put("ORGANIZATION_ID", String.valueOf(organizationId));
				entity.getDefaultValues().put("CREATION_DATE", "SYSDATE()");
				entity.getDefaultValues().put("LAST_UPDATE_DATE", "SYSDATE()");
				entity.getDefaultValues().put("VERSION", String.valueOf(1));
				
				importDataFile(entity);
				
				if (entity.getPostImport() != null && entity.getPostImport().trim().length() > 0) {
					if (Class.forName(entity.getPostImport()).getSuperclass().isAssignableFrom(ImportTask.class)) {
						ImportTask task = (ImportTask) Class.forName(entity.getPostImport()).newInstance();
						task.run(emf, connection, entity);
					}
				}
				
				deleteDataFile(entity.getDataFile());
			}		
		} catch (Exception e) {
			throw new DataImportException(e);
		}	
	}
	
	private void createDataFile(String type, Entity entity) throws IOException, ConnectionException {	
		DataExporter exporter = new DataExporter();
		String query = buildQuery(entity.getSobject(), entity.getFields(), entity.getWhereClause());
		String fileName = outputDirectory.concat("/" + type + "_" + organizationId + ".csv");
		int size = exporter.createFile(connection, fileName, getDelimiter(), getEnclosedBy(), query);
		entity.setQueryResultSize(size);
		entity.setDataFile(fileName);
	}

	private void importDataFile(Entity entity) {
		String importString = buildImportString(entity.getDataFile(), entity.getTable(), entity.getFields(), entity.getDefaultValues());
		TableImporter work = new TableImporter();
		work.setImportStatement(importString);

		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		session.doWork(work);
		
		entity.setRecordsImported(work.getRecordCount());
	}
	
	private String buildQuery(String sobject, Map<String,String> fields, String whereClause) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select ");
		int i = 0;
		for (String field : fields.values()) {
			sb.append(field);
			if (++i < fields.values().size()) {
				sb.append(", ");
			}
		}
		sb.append(" From ");
		sb.append(sobject);
		if (whereClause != null && whereClause.trim().length() > 0) {
			sb.append(" Where ");
			sb.append(whereClause.trim());
		}
		return sb.toString();
	}
	
	private String buildImportString(String infile, String table, Map<String, String> fields, Map<String, String> defaultValues) {
		StringBuffer sb = new StringBuffer();
		sb.append("LOAD DATA LOCAL INFILE '");
		sb.append(infile);
		sb.append("' INTO TABLE ");
		sb.append(table);
		sb.append(" FIELDS TERMINATED BY '");
		sb.append(getDelimiter());
		sb.append("' ENCLOSED BY '");
		sb.append(getEnclosedBy());
		sb.append("' LINES TERMINATED BY '");
		sb.append(System.getProperty("line.separator"));
		sb.append("' IGNORE 1 LINES ");
		sb.append("(");
		int i = 0;
		for (String field : fields.keySet()) {
			sb.append(field);
			if (++i < fields.size()) {
				sb.append(", ");
			}
		}
		i = 0;
		sb.append(") ");
		if (defaultValues != null && defaultValues.isEmpty() == false) {
			sb.append("SET ");
			Set<String> fieldSet = defaultValues.keySet();
			for (String field : fieldSet) {
				sb.append(field);
				sb.append(" = ");
				sb.append(defaultValues.get(field));
				if (++i < defaultValues.size()) {
					sb.append(", ");
				}
			}
		}
		return sb.toString();		
	}
	
	private void deleteDataFile(String dataFile) {
		File file = new File(dataFile);
		file.delete();
	}
	
	private char getDelimiter() {
		return ',';
	}
	
	private char getEnclosedBy() {
		return '"';
	}
}