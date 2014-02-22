package com.nowellpoint.handler.dataimport.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4265173841040033717L;
	
	/**
	 * 
	 */
	
	@Column(name = "USER_ROLE_ID", nullable = false, unique = true)
	private String userRoleId;
	
	/**
	 * 
	 */
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	/**
	 * 
	 */
	
	@OneToOne(targetEntity = UserRole.class)
    @JoinColumn(name="PARENT_ROLE_ID", referencedColumnName = "ID", updatable = true, insertable = true)
    private UserRole parentRoleId;
	
	public UserRole() {
		
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(UserRole parentRoleId) {
		this.parentRoleId = parentRoleId;
	}
}