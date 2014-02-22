package com.nowellpoint.handler.dataimport.test;

import java.io.InputStream;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nowellpoint.handler.dataimport.DataImporter;
import com.nowellpoint.handler.dataimport.test.model.Organization;
import com.nowellpoint.handler.dataimport.test.model.User;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class DataImportTest {

	private static PartnerConnection connection;
	private static EntityManagerFactory emf;
	private static Organization organization;
	private static User user;
	
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
		
		emf = Persistence.createEntityManagerFactory("nowellpoint");
	}
	
	@Before
	public void setupData() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		QueryResult qr = null;
		SObject sobject = null;
		
		try {
			qr = connection.query("Select Id, City, Country, Name, "
					+ "PostalCode, State, Street From Organization "
					+ "Where Id = " + connection.getUserInfo().getOrganizationId());
			
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		
		sobject = qr.getRecords()[0];
				
		organization = new Organization();
		organization.setCity(sobject.getField("City").toString());
		organization.setCountry(sobject.getField("Country").toString());
		organization.setDefaultLocaleSidKey("en_US");
		organization.setFiscalYearStartMonth(3);
		organization.setName(sobject.getField("Name").toString());
		organization.setOrganizationId(sobject.getField("Id").toString());
		organization.setPostalCode(sobject.getField("PostalCode").toString());
		organization.setState(sobject.getField("State").toString());
		organization.setStreet(sobject.getField("Street").toString());		
		
		em.persist(organization);
		
		try {
			qr = connection.query("Select Id, Alias, City, Country, Name, "
					+ "PostalCode, State, Street, CommunityNickname, CompanyName, "
					+ "Email, FirstName, LastName, MobilePhone, Title, UserType, "
					+ "UserName From User Where Id = " + connection.getUserInfo().getUserId());
			
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		
		sobject = qr.getRecords()[0];
		
		user = new User();
		user.setOrganization(organization);
		user.setAlias(sobject.getField("Alias").toString());
		user.setCity(sobject.getField("City").toString());
		user.setCommunityNickname(sobject.getField("CommunityNickname").toString());
		user.setCompanyName(sobject.getField("CompanyName").toString());
		user.setCountry(sobject.getField("Country").toString());
		user.setEmail(sobject.getField("Email").toString());
		user.setFirstName(sobject.getField("FirstName").toString());
		user.setIsActive(true);
		user.setLastName(sobject.getField("LastName").toString());
		user.setMobilePhone(sobject.getField("MobilePhone").toString());
		user.setName(sobject.getField("Name").toString());
		user.setStreet(sobject.getField("Street").toString());
		user.setState(sobject.getField("State").toString());
		user.setTimeZoneSidKey("America/Los_Angeles");
		user.setTitle(sobject.getField("Title").toString());
		user.setUserId(sobject.getField("Id").toString());
		user.setUserType(sobject.getField("UserType").toString());
		user.setUsername(sobject.getField("UserName").toString());
		
		em.persist(user);
		
		em.getTransaction().commit();
		em.close();
	}
	
	@AfterClass
	public static void disconnect() {
		Assume.assumeNotNull(connection.getConfig().getSessionId());
		try {
			connection.logout();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		
		emf.close();
	}
	
	@Test
	public void testDataImporter() {
		InputStream is = this.getClass().getResourceAsStream("/salesforce-mapping.json");
		Scanner scanner = new Scanner(is,"UTF-8");
		String mappingFile = scanner.useDelimiter("\\A").next();
		scanner.close();

		DataImporter importer = new DataImporter(mappingFile, organization.getId(), user.getId(), connection, emf, "/tmp");
		importer.runFullImport();
	}
}