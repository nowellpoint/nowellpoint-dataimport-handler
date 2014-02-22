package com.nowellpoint.handler.dataimport.config;

import java.io.Serializable;
import java.util.List;

public class EntityMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6803727929242386101L;
	
	/**
	 * 
	 */
	
	private Long organizationId;
	
	/**
	 * 
	 */
	
	private List<Entities> entities;
	
	
	public EntityMapping() {
		
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public List<Entities> getEntities() {
		return entities;
	}

	public void setEntities(List<Entities> entities) {
		this.entities = entities;
	}
}