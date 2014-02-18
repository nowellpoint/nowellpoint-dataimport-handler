package com.nowellpoint.salesforce.client.model;

import java.io.Serializable;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 290751712672557832L;
	
	/**
	 * 
	 */
	
	private String table;
	
	/**
	 * 
	 */
	
	private String sobject;
	
	/**
	 * 
	 */
	
	private Map<String, String> fields;
	
	/**
	 * 
	 */
	
	private String whereClause;
	
	/**
	 * 
	 */
	
	private Map<String, String> defaultValues;
	
	/**
	 * 
	 */
	
	private Integer queryResultSize;
	
	/**
	 * 
	 */
	
	private String dataFile;
	
	/**
	 * 
	 */
	
	private Integer recordsImported;
	

	public Entity() {
		
	}
	
	public void addField(String target, String source) {
		fields.put(target.toUpperCase(), source);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getSobject() {
		return sobject;
	}

	public void setSobject(String sobject) {
		this.sobject = sobject;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public Map<String, String> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(Map<String, String> defaultValues) {
		this.defaultValues = defaultValues;
	}

	public Integer getQueryResultSize() {
		return queryResultSize;
	}

	public void setQueryResultSize(Integer queryResultSize) {
		this.queryResultSize = queryResultSize;
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public Integer getRecordsImported() {
		return recordsImported;
	}

	public void setRecordsImported(Integer recordsImported) {
		this.recordsImported = recordsImported;
	}
}