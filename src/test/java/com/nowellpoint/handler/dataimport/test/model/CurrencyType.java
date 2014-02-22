package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		name="CURRENCY_TYPE", 
		uniqueConstraints = @UniqueConstraint(columnNames = {"CURRENCY_TYPE_ID"}))
@NamedQuery(
		name="CurrencyType.queryAll",
		query="SELECT ct FROM CurrencyType ct")

public class CurrencyType extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6644890024294006460L;

	/**
	 * 
	 */

	public static final String QUERY_ALL = "CurrencyType.queryAll";

	/**
	 * 
	 */
	
	@Column(name="CURRENCY_TYPE_ID", length=20, insertable=true, updatable=false, unique=true, nullable=false)
	private String currencyTypeId;

	/**
	 * 
	 */
	
	@Column(name="ISO_CODE", nullable=false, length=3)
	private String isoCode;
	
	/**
	 * 
	 */
	
	@Column(name = "DECIMAL_PLACES", nullable = false)
	private Integer decimalPlaces;
	
	/**
	 * 
	 */
	
	@Column(name = "IS_CORPORATE", nullable = false)
	private Boolean isCorporate;
	
	/**
	 * 
	 */
	
	@Column(name = "CONVERSION_RATE")
	private Double conversionRate;

	/**
	 * 
	 */
	
	@Column(name="IS_ACTIVE")
	private Boolean isActive;	


	public CurrencyType() {

	}

	public String getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(String currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public Integer getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(Integer decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	public Boolean getIsCorporate() {
		return isCorporate;
	}

	public void setIsCorporate(Boolean isCorporate) {
		this.isCorporate = isCorporate;
	}

	public Double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Double conversionRate) {
		this.conversionRate = conversionRate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}