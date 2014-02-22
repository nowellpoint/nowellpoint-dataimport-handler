package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@NamedQueries({
	@NamedQuery(name = "User.findByUserId", query = "Select u "
			+ "From User u "
			+ "Where u.userId = :userId"),
	@NamedQuery(name = "User.findByApiKey", query = "Select u "
			+ "From User u "
			+ "Where apiKey = :apiKey")
})
public class User extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8608331954991392574L;
	
	/**
	 * 
	 */
	
	public static final String FIND_BY_USER_ID = "User.findByUserId";
	
	/**
	 * 
	 */
	
	public static final String FIND_BY_API_KEY = "User.findByApiKey";
	
	/**
	 * 
	 */
	
	@Column(name = "USER_ID", length=20, insertable=true, updatable=false, nullable=false)
	private String userId;
	
	/**
	 * 
	 */
	
	@Column(name= "USERNAME", length = 100, unique = true)
	private String username;
	
	/**
	 * 
	 */
	
	@Column(name = "PASSWORD", length = 100)
	private String password;
	
	/**
	 * 
	 */
	
	@Column(name= "LAST_NAME")
	private String lastName;
	
	/**
	 * 
	 */
	
	@Column(name= "FIRST_NAME")
	private String firstName;
	
	/**
	 * 
	 */
	
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 
	 */
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	/**
	 * 
	 */
	
	@Column(name = "DIVISION")
	private String division;
	
	/**
	 * 
	 */
	
	@Column(name = "DEPARTMENT")
	private String department;
	
	/**
	 * 
	 */
	
	@Column(name = "TITLE")
	private String title;
	
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
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	/**
	 * 
	 */
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	/**
	 * 
	 */
	
	@Column(name = "EMAIL")
	private String email;
	
	/**
	 * 
	 */
	
	@Column(name = "SENDER_EMAIL")
	private String senderEmail;
	
	/**
	 * 
	 */
	
	@Column(name = "SENDER_NAME")
	private String senderName;
	
	/**
	 * 
	 */
	
	@Column(name = "SIGNATURE")
	private String signature;
	
	/**
	 * 
	 */
	
	@Column(name = "PHONE")
	private String phone;
	
	/**
	 * 
	 */
	
	@Column(name = "EXTENSION")
	private String extension;
	
	/**
	 * 
	 */
	
	@Column(name = "FAX")
	private String fax;
	
	/**
	 * 
	 */
	
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	
	/**
	 * 
	 */
	
	@Column(name = "ALIAS")
	private String alias;
	
	/**
	 * 
	 */
	
	@Column(name = "COMMUNITY_NICKNAME")
	private String communityNickname;
	
	/**
	 * 
	 */
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	/**
	 * 
	 */
	
	@Column(name = "TIMEZONE_SID_KEY")
	private String timeZoneSidKey;
	
	/**
	 * 
	 */
	
	@Column(name = "LOCALE_SID_KEY")
	private String localeSidKey;
	
	/**
	 * 
	 */
	
	@Column(name = "EMAIL_ENCODING_KEY")
	private String emailEncodingKey;
	
	/**
	 * 
	 */
	
	@Column(name = "PERMISSIONS_CUSTOMIZE_APPLICATION")
	private Boolean permissionsCustomizeApplication;
	
	/**
	 * 
	 */
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	/**
	 * 
	 */
	
	@Column(name = "LANGUAGE_LOCALE_KEY")
	private String languageLocaleKey;
	
	/**
	 * 
	 */
	
	@Column(name = "EMPLOYEE_NUMBER")
	private String employeeNumber;
	
	/**
	 * 
	 */
	
	@Column(name = "DELEGATED_APPROVER_ID")
	private String delegatedApproverId;
	
	/**
	 * 
	 */
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="MANAGER_ID", referencedColumnName = "ID", updatable = true, insertable = true)
	private User manager;
	
	/**
	 * 
	 */
	
	@OneToOne(targetEntity = UserRole.class)
	@JoinColumn(name="USER_ROLE_ID", referencedColumnName = "ID", updatable = true, insertable = true)
	private UserRole userRole;
	
	/**
	 * 
	 */
	
	@Column(name = "ABOUT_ME")
	private String aboutMe;
	
	/**
	 * 
	 */
	
	@Column(name = "API_KEY", length = 25)
	private String apiKey;
	
	/**
	 * 
	 */
	
	@Column(name = "MODIFY_SETTINGS")
	private Boolean modifySettings;
	
	public User() {
		
	}	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCommunityNickname() {
		return communityNickname;
	}

	public void setCommunityNickname(String communityNickname) {
		this.communityNickname = communityNickname;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getTimeZoneSidKey() {
		return timeZoneSidKey;
	}

	public void setTimeZoneSidKey(String timeZoneSidKey) {
		this.timeZoneSidKey = timeZoneSidKey;
	}

	public String getLocaleSidKey() {
		return localeSidKey;
	}

	public void setLocaleSidKey(String localeSidKey) {
		this.localeSidKey = localeSidKey;
	}

	public String getEmailEncodingKey() {
		return emailEncodingKey;
	}

	public void setEmailEncodingKey(String emailEncodingKey) {
		this.emailEncodingKey = emailEncodingKey;
	}

	public Boolean getPermissionsCustomizeApplication() {
		return permissionsCustomizeApplication;
	}

	public void setPermissionsCustomizeApplication(Boolean permissionsCustomizeApplication) {
		this.permissionsCustomizeApplication = permissionsCustomizeApplication;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLanguageLocaleKey() {
		return languageLocaleKey;
	}

	public void setLanguageLocaleKey(String languageLocaleKey) {
		this.languageLocaleKey = languageLocaleKey;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getDelegatedApproverId() {
		return delegatedApproverId;
	}

	public void setDelegatedApproverId(String delegatedApproverId) {
		this.delegatedApproverId = delegatedApproverId;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
		
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public Boolean getModifySettings() {
		return modifySettings;
	}

	public void setModifySettings(Boolean modifySettings) {
		this.modifySettings = modifySettings;
	}
}