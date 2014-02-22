package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PRICEBOOK_ENTRY")
public class PricebookEntry extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5875485819039949981L;

	/**
	 * 
	 */
	
	@Column(name="PRICEBOOK_ENTRY_ID", length=20, insertable=true, updatable=false, unique=true, nullable=false)
	private String pricebookEntryId;
	
	/**
	 * 
	 */
	
	@Column(name="CURRENCY_ISO_CODE", length=3)
	private String currencyIsoCode;

	/**
	 * 
	 */
	
	@Column(name="UNIT_PRICE")
	private Double unitPrice;

	/**
	 * 
	 */
	
	@Column(name="IS_ACTIVE")
	private Boolean isActive;

	/**
	 * 
	 */
	
	@Column(name="IS_DELETED")
	private Boolean isDeleted;

	/**
	 * 
	 */
	
	@OneToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID", nullable = false)
	private Product product;

	/**
	 * 
	 */
	
	@OneToOne
    @JoinColumn(name = "PRICEBOOK_ID", referencedColumnName = "ID", nullable = false)
	private Pricebook pricebook;

	
	public PricebookEntry() {

	}

	public String getPricebookEntryId() {
		return pricebookEntryId;
	}

	public void setPricebookEntryId(String pricebookEntryId) {
		this.pricebookEntryId = pricebookEntryId;
	}

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public void setCurrencyIsoCode(String currencyIsoCode) {
		this.currencyIsoCode = currencyIsoCode;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Pricebook getPricebook() {
		return pricebook;
	}

	public void setPricebook(Pricebook pricebook) {
		this.pricebook = pricebook;
	}
}