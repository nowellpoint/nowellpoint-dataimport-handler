package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "ORGANIZATION")
@NamedQueries({ 
	@NamedQuery(name = "findByOrganizationId", query = "Select o From Organization o Where o.organizationId = :organizationId")
})
public class Organization implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 4347536133780744954L;
	
	/**
	 * 
	 */
	
	public static final String FIND_BY_ORGANIZATION_ID = "findByOrganizationId";
	
	/**
	 * 
	 */
	
	@Id
	@Column(name="ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	/**
	 * 
	 */
	
	@Column(name = "ORGANIZATION_ID")
	private String organizationId;
	
	/**
	 * 
	 */
	
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 
	 */
	
	@Column(name = "DIVISION")
	private String division;
	
	/**
	 * 
	 */
	
	@Column(name = "STREET")
	private String street;
	
	/**
	 * 
	 */
	
	@Column(name = "CITY")
	private String city;
	
	/**
	 * 
	 */
	
	@Column(name = "STATE")
	private String state;
	
	/**
	 * 
	 */
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	/**
	 * 
	 */
	
	@Column(name = "COUNTRY")
	private String country;
	
	/**
	 * 
	 */
	
	@Column(name = "PRIMARY_CONTACT")
	private String primaryContact;
	
	/**
	 * 
	 */
	
	@Column(name = "DEFAULT_LOCALE_SID_KEY")
	private String defaultLocaleSidKey;
	
	/**
	 * 
	 */
	
	@Column(name = "LANGUAGE_LOCALE_KEY")
	private String languageLocaleKey;
	
	/**
	 * 
	 */
	
	@Column(name = "FISCAL_YEAR_START_MONTH")
	private Integer fiscalYearStartMonth;
	
	/**
	 * 
	 */
	
	@Column(name = "REGISTRATION_CODE")
	private String registrationCode;
	
	/**
	 * 
	 */
	
	@Column(name = "REGISTRATION_DATE")
	private Timestamp registrationDate;
	
	/**
	 * 
	 */
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name="REGISTERED_BY", referencedColumnName = "ID")
	private User registeredBy; 
	
	/**
	 * 
	 */
	
	@Column(name = "CREATION_DATE", updatable = false, insertable = true, nullable = false)
	private Timestamp creationDate;
	
	/**
	 * 
	 */
	
	@Column(name = "SESSION_TIMEOUT", nullable = false)
	private Integer sessionTimeout;
	
	
	public Organization() {
		setSessionTimeout(120);
	}
	
	@PrePersist
	public void prePersist() {
		setCreationDate(new Timestamp(new Date().getTime()));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getDefaultLocaleSidKey() {
		return defaultLocaleSidKey;
	}

	public void setDefaultLocaleSidKey(String defaultLocaleSidKey) {
		this.defaultLocaleSidKey = defaultLocaleSidKey;
	}

	public String getLanguageLocaleKey() {
		return languageLocaleKey;
	}

	public void setLanguageLocaleKey(String languageLocaleKey) {
		this.languageLocaleKey = languageLocaleKey;
	}

	public Integer getFiscalYearStartMonth() {
		return fiscalYearStartMonth;
	}

	public void setFiscalYearStartMonth(Integer fiscalYearStartMonth) {
		this.fiscalYearStartMonth = fiscalYearStartMonth;
	}
	
	public String getRegistrationCode() {
		return registrationCode;
	}
	
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	public User getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(User registeredBy) {
		this.registeredBy = registeredBy;
	}
	
	public Timestamp getCreationDate() {
		return creationDate;
	}
	
	private void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
}