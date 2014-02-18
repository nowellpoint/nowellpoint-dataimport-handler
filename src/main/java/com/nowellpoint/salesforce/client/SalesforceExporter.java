package com.nowellpoint.salesforce.client;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.bind.XmlObject;

public class SalesforceExporter implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -3514183553778686670L;

	/**
	 * 
	 * @param connection
	 * @param fileName
	 * @param delimiter
	 * @param enclosedBy
	 * @param query
	 * @throws IOException
	 * @throws ConnectionException
	 */
	
	public int createFile(PartnerConnection connection, String fileName, char delimiter, char enclosedBy, String query) throws IOException, ConnectionException {								
		QueryResult queryResult = getQueryResult(connection, query);
		buildDataFile(connection, queryResult, fileName, delimiter, enclosedBy);
		return queryResult.getSize();
	}
	
	/**
	 * 
	 * @param connection
	 * @param query
	 * @return
	 * @throws ConnectionException
	 */
	
	private QueryResult getQueryResult(PartnerConnection connection, String query) throws ConnectionException {
		return connection.query(query);
	}
	
	/**
	 * 
	 * @param connection
	 * @param queryResult
	 * @param fileName
	 * @param delimiter
	 * @param enclosedBy
	 * @throws IOException
	 * @throws ConnectionException
	 */
	
	private void buildDataFile(PartnerConnection connection, QueryResult queryResult, String fileName, char delimiter, char enclosedBy) throws IOException, ConnectionException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName), delimiter, enclosedBy);
				
		boolean done = false;
		int i = 0;
		
		while (! done) {
			for (SObject sobject : queryResult.getRecords()) {
				Iterator<XmlObject> iterator = sobject.getChildren();
				List<String> header = new ArrayList<String>();
				List<String> record = new ArrayList<String>();
				while (iterator.hasNext()) {
					XmlObject xmlObject = iterator.next();
					if (! "type".equals(xmlObject.getName().getLocalPart())) {
						if (! header.contains(xmlObject.getName().getLocalPart())) {
							header.add(xmlObject.getName().getLocalPart());
							record.add(xmlObject.getValue() != null ? xmlObject.getValue().toString() : "");
						}
					}
				}
				if (i == 0) {
					writer.writeNext(header.toArray(new String[header.size()]));
				}
				writer.writeNext(record.toArray(new String[record.size()]));
				i++;
			}
			
			if (queryResult.isDone()) {
				done = true;
			} else {
		    	queryResult = connection.queryMore(queryResult.getQueryLocator());
		    }
		}
		writer.close();
	}
}
