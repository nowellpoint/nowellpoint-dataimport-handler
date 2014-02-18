package com.nowellpoint.salesforce.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.jdbc.Work;

public class ManagerHierarchyUpdate implements Work {
	
	private String selectSQL = "SELECT ID FROM USER WHERE USER_ID = :managerId";
	private String updateSQL = "UPDATE USER SET MANAGER_ID = :id WHERE USER_ID = :userId";
	private String userId;
	private String managerId;
	
	public ManagerHierarchyUpdate(String userId, String managerId) {
		this.userId = userId;
		this.managerId = managerId;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL.replace(":managerId", managerId));
		rs.next();
		Long id = rs.getLong(1);
		stmt.executeUpdate(updateSQL.replace(":id", id.toString()).replace(":userId", userId));
	}
}