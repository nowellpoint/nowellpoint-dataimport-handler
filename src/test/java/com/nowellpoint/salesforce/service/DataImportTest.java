package com.nowellpoint.salesforce.service;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nowellpoint.salesforce.client.DataImporter;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class DataImportTest {

	private static PartnerConnection connection;
	private static EntityManagerFactory entityManagerFactory;
	
	@BeforeClass
	public static void connect() {	
		ConnectorConfig config = new ConnectorConfig();
		config.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/29.0");
		config.setUsername(System.getenv("SALESFORCE_USERNAME"));
		config.setPassword(System.getenv("SALESFORCE_PASSWORD") + System.getenv("SALESFORCE_SECURITY_TOKEN"));
		
		try {
			connection = Connector.newConnection(config);
			System.out.println("Session Id: " + connection.getConfig().getSessionId());
			System.out.println("Service Endpoint: " + connection.getConfig().getServiceEndpoint());
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		
		entityManagerFactory = Persistence.createEntityManagerFactory("nowellpoint");
	}
	
	@AfterClass
	public static void disconnect() {
		Assume.assumeNotNull(connection.getConfig().getSessionId());
		try {
			connection.logout();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testManagerHierarchy() {
		try {
			new DataImporter().setEntityManagerFactory(entityManagerFactory)
			.buildManagerHierarchy("/tmp/user.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDataImporter() {
		new DataImporter().setEntityManagerFactory(entityManagerFactory)
				.setOutputDirectory("/tmp")
				.setMappingFile("salesforce-mapping.json")
				.setPartnerConnection(connection)
				.setOrganizationId(new Long(1))
				.setUserId(new Long(1))
				.run();
		
		
	}
}