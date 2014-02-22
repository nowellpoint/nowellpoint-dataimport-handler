package com.nowellpoint.handler.dataimport.exception;

public class DataImportException extends RuntimeException {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 8349044940576763444L;
	
	public DataImportException(Exception e) {
		super(e);
	}
}