package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
public class Product extends BaseEntity implements Serializable {

    /**
     * 
     */
    
    private static final long serialVersionUID = 5193639212025075411L;
    
    /**
     * 
     */
    
    @Column(name="PRODUCT_ID", length=20, insertable=true, updatable=false, unique=true, nullable=false)
    private String productId;

    /**
     * 
     */
    
    @Column(name="NAME")
    private String name;

    /**
     * 
     */
    
    @Column(name="DESCRIPTION")
    private String description;

    /**
     * 
     */
    
    @Column(name="FAMILY")
    private String family;

    /**
     * 
     */
    
    @Column(name="PRODUCT_CODE")
    private String productCode;

    /**
     * 
     */
    
    @Column(name="IS_ACTIVE")
    private Boolean isActive;

    /**
     * 
     */
    
    @Column(name="CURRENCY_ISO_CODE", length=3)
    private String currencyIsoCode;

    /**
     * 
     */
    
    @Column(name="IS_DELETED")
    private Boolean isDeleted;
    
    
    public Product() {

    }

    public String getProductId() {
            return productId;
    }

    public void setProductId(String productId) {
            this.productId = productId;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public String getFamily() {
            return family;
    }

    public void setFamily(String family) {
            this.family = family;
    }

    public String getProductCode() {
            return productCode;
    }

    public void setProductCode(String productCode) {
            this.productCode = productCode;
    }

    public Boolean getIsActive() {
            return isActive;
    }

    public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
    }

    public String getCurrencyIsoCode() {
            return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
            this.currencyIsoCode = currencyIsoCode;
    }

    public Boolean getIsDeleted() {
            return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
    }
}