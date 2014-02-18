package com.nowellpoint.salesforce.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;

import au.com.bytecode.opencsv.CSVReader;

import com.nowellpoint.salesforce.client.model.Entities;
import com.nowellpoint.salesforce.client.model.Entity;
import com.nowellpoint.salesforce.client.model.EntityMapping;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

public class DataImporter {
	
	private Long organizationId;
	private Long userId;
	private String mappingFile;
	private PartnerConnection connection;
	private String outputDirectory;
	private EntityManagerFactory entityManagerFactory;
	private SalesforceExporter exporter;
	
	public DataImporter() {
		exporter = new SalesforceExporter();
	}
	
	public DataImporter setMappingFile(String mappingFile) {
		this.mappingFile = mappingFile;
		return this;
	}

	public DataImporter setPartnerConnection(PartnerConnection connection) {
		this.connection = connection;
		return this;
	}

	public DataImporter setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
		return this;
	}

	public DataImporter setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		return this;
	}
	
	public DataImporter setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
		return this;
	}
	
	public DataImporter setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	public void run() {
		ObjectMapper om = new ObjectMapper();
		try {
			EntityMapping mapping = om.readValue(this.getClass().getResourceAsStream("/" + mappingFile), EntityMapping.class);
			for (Entities entities : mapping.getEntities()) {
				String type = entities.getType();
				Entity entity = entities.getEntity();	
				createDataFile(type, entity);
				
				if (entity.getDefaultValues() == null) {
					entity.setDefaultValues(new HashMap<String, String>());
				}
				
				entity.getDefaultValues().put("CREATED_BY_ID", String.valueOf(userId));
				entity.getDefaultValues().put("LAST_MODIFIED_BY_ID", String.valueOf(userId));
				entity.getDefaultValues().put("ORGANIZATION_ID", String.valueOf(organizationId));
				entity.getDefaultValues().put("CREATION_DATE", "SYSDATE()");
				entity.getDefaultValues().put("LAST_UPDATE_DATE", "SYSDATE()");
				entity.getDefaultValues().put("VERSION", String.valueOf(1));
				
				//importDataFile(entity);
				
				System.out.println("Exported..." + entity.getQueryResultSize());
				System.out.println("Impored..." + entity.getRecordsImported());
			}		
		} catch (IOException | ConnectionException e) {
			e.printStackTrace();
		}	
		
		try {
			buildManagerHierarchy("/tmp/user_1.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createDataFile(String type, Entity entity) throws IOException, ConnectionException {		
		String query = buildQuery(entity.getSobject(), entity.getFields(), entity.getWhereClause());
		String fileName = outputDirectory.concat("/" + type + "_" + organizationId + ".csv");
		int size = exporter.createFile(connection, fileName, getDelimiter(), getEnclosedBy(), query);
		entity.setQueryResultSize(size);
		entity.setDataFile(fileName);
	}

	private void importDataFile(Entity entity) {
		String importString = buildImportString(entity.getDataFile(), entity.getTable(), entity.getFields(), entity.getDefaultValues());
		MySqlImporter work = new MySqlImporter();
		work.setImportStatement(importString);

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = entityManager.unwrap(Session.class);
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
		System.out.println(sb.toString());
		return sb.toString();		
	}
	
	public void buildManagerHierarchy(String dataFile) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(dataFile), getDelimiter(), getEnclosedBy());
		int managerIdColumn = 0;
		int userIdColumn = 0;
		String [] columns = reader.readNext();
		for (int i = 0; i < columns.length; i++) {
			if ("ManagerId".equalsIgnoreCase(columns[i])) {
				managerIdColumn = i;
			}
			if ("UserId".equalsIgnoreCase(columns[i])) {
				userIdColumn = i;
			}
		}
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String selectSQL = "SELECT ID FROM USER WHERE USER_ID = :managerId";
		String updateSQL = "UPDATE USER SET MANAGER_ID = :id WHERE USER_ID = :userId";
		
		String [] nextLine = reader.readNext();
	    while ((nextLine = reader.readNext()) != null) {
	    	String managerId = nextLine[managerIdColumn];
	    	String userId = nextLine[userIdColumn];
	    	if (managerId.trim().length() > 0) {
	    		System.out.println("ManagerId: " + managerId);
		    	System.out.println("UserId: " + userId);

				Session session = entityManager.unwrap(Session.class);
				session.doWork(new ManagerHierarchyUpdate(userId, managerId));
	    	}
	    }
		reader.close();
		entityManager.close();
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