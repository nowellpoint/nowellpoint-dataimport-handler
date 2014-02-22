package com.nowellpoint.handler.dataimport.config;

import java.io.Serializable;

public class Entities implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 3644506159208991336L;
	
	/**
	 * 
	 */
	
	private String type;
	
	/**
	 * 
	 */
	
	private Entity entity;
	
	public Entities() {
		
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}