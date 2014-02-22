package com.nowellpoint.handler.dataimport.task;

import java.io.FileReader;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;

import au.com.bytecode.opencsv.CSVReader;

import com.nowellpoint.handler.dataimport.config.Entity;
import com.nowellpoint.handler.dataimport.mysql.ManagerHierarchyUpdate;
import com.sforce.soap.partner.PartnerConnection;

public class UserPostImport extends ImportTask implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5906776658387483822L;
	
	public UserPostImport() {
		
	}

	@Override
	public void run(EntityManagerFactory entityManagerFactory, PartnerConnection connection, Entity entity) throws Exception {
		CSVReader reader = new CSVReader(new FileReader(entity.getDataFile()), entity.getDelimeter().charAt(0), entity.getEnclosedBy().charAt(0));
		int col1 = 0;
		int col2 = 0;
		String [] columns = reader.readNext();
		for (int i = 0; i < columns.length; i++) {
			if ("ManagerId".equalsIgnoreCase(columns[i])) {
				col1 = i;
			}
			if ("UserId".equalsIgnoreCase(columns[i])) {
				col2 = i;
			}
		}
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		
		String [] nextLine = reader.readNext();
	    while ((nextLine = reader.readNext()) != null) {
	    	String managerId = nextLine[col1];
	    	String userId = nextLine[col2];
	    	if (managerId.trim().length() > 0) {
		    	session.doWork(new ManagerHierarchyUpdate(userId, managerId));
	    	}
	    }
		reader.close();
		entityManager.close();
	}
}