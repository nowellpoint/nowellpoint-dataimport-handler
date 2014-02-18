package com.nowellpoint.salesforce.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

public class MySqlImporter implements Work {

	private String importStatement;
	
	private int recordCount;

	public void setImportStatement(String importStatement) {
		this.importStatement = importStatement;
	}
	
	public int getRecordCount() {
		return recordCount;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(importStatement);
		connection.setAutoCommit(Boolean.FALSE);
		recordCount = statement.executeUpdate();
		connection.commit();
	}
}