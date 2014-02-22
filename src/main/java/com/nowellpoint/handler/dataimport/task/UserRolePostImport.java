package com.nowellpoint.handler.dataimport.task;

import java.io.FileReader;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;

import au.com.bytecode.opencsv.CSVReader;

import com.nowellpoint.handler.dataimport.config.Entity;
import com.nowellpoint.handler.dataimport.mysql.ParentUserRoleUpdate;
import com.sforce.soap.partner.PartnerConnection;

public class UserRolePostImport extends ImportTask implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5906776658387483822L;
	
	public UserRolePostImport() {
		
	}

	@Override
	public void run(EntityManagerFactory entityManagerFactory, PartnerConnection connection, Entity entity) throws Exception {
		CSVReader reader = new CSVReader(new FileReader(entity.getDataFile()), entity.getDelimeter().charAt(0), entity.getEnclosedBy().charAt(0));
		int col1 = 0;
		int col2 = 0;
		String [] columns = reader.readNext();
		for (int i = 0; i < columns.length; i++) {
			if ("Id".equalsIgnoreCase(columns[i])) {
				col1 = i;
			}
			if ("ParentRoleId".equalsIgnoreCase(columns[i])) {
				col2 = i;
			}
		}
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		
		String [] nextLine = reader.readNext();
	    while ((nextLine = reader.readNext()) != null) {
	    	String id = nextLine[col1];
	    	String parentRoleId = nextLine[col2];
	    	if (parentRoleId.trim().length() > 0) {
		    	session.doWork(new ParentUserRoleUpdate(id, parentRoleId));
	    	}
	    }
		reader.close();
		entityManager.close();
	}
}