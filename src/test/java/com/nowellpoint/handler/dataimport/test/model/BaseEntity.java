package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5656637717184021985L;
	
    /**
     * 
     */
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
	
	/**
	 * 
	 */
	
	@Version
	@Column(name = "VERSION")
	private Long version;
	
	/**
	 * 
	 */
	
	@OneToOne(targetEntity = User.class)
    @JoinColumn(name="CREATED_BY_ID", referencedColumnName = "ID", updatable = false, insertable = true)
    private User createdBy;
	
	/**
	 * 
	 */
	
	@Column(name = "CREATION_DATE", nullable = false, updatable = false)
	private Timestamp creationDate;
	
	/**
	 * 
	 */
	
	@OneToOne(targetEntity = User.class)
    @JoinColumn(name="LAST_MODIFIED_BY_ID", referencedColumnName = "ID", updatable = true, insertable = true)
    private User lastModifiedBy;
	
	/**
	 * 
	 */
	
	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Timestamp lastUpdateDate;
	
	/**
	 * 
	 */
	
	@ManyToOne(targetEntity = Organization.class)
	@JoinColumn(name="ORGANIZATION_ID", referencedColumnName = "ID", updatable = false, insertable = true, nullable = false)
	private Organization organization;
	
	
	
	@PrePersist
	public void prePersist() {
		Timestamp now = new Timestamp(new Date().getTime());
		setCreationDate(now);
		setLastUpdateDate(now);
	}
	
	@PreUpdate
	public void preUpdate() {
		setLastUpdateDate(new Timestamp(new Date().getTime()));
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	private void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	private void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public Organization getOrganization() {
		return organization;
	}
	
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}