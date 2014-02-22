package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		name="PRICEBOOK", 
		uniqueConstraints = @UniqueConstraint(columnNames={"PRICEBOOK_ID"}))
@NamedQuery(
		name="Pricebook.queryAll",
		query="SELECT p FROM Pricebook p")

public class Pricebook extends BaseEntity implements Serializable {
		
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 8813788762641791061L;
	
	/**
	 * 
	 */
	
	public static final String QUERY_ALL = "Pricebook.queryAll";

	/**
	 * 
	 */
	
	@Column(name="PRICEBOOK_ID", length=20, insertable=true, updatable=false, unique=true, nullable=false)
	private String pricebookId;

	/**
	 * 
	 */
	
	@Column(name = "NAME", length=60)
	private String name;
	
	/**
	 * 
	 */
	
	@Column(name = "CURRENCY_ISO_CODE", length = 3)
	private String currencyIsoCode;
	
	/**
	 * 
	 */
	
	@Column(name = "DESCRIPTION", length = 255)
	private String description;
	
	/**
	 * 
	 */
	
	@Column(name="IS_ENABLED")
	private Boolean isEnabled;	
	
	
	public Pricebook() {
		setIsEnabled(Boolean.FALSE);
	}
	
	public String getPricebookId() {
		return pricebookId;
	}
	
	public void setPricebookId(String pricebookId) {
		this.pricebookId = pricebookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}