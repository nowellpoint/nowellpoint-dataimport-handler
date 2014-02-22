package com.nowellpoint.handler.dataimport.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

public class ParentUserRoleUpdate implements Work{

	private String selectSQL = "SELECT ID FROM USER_ROLE WHERE USER_ROLE_ID = ?";
	private String updateSQL = "UPDATE USER_ROLE SET PARENT_ROLE_ID = ? WHERE USER_ROLE_ID = ?";
	private String userRoleId;
	private String parentRoleId;
	
	public ParentUserRoleUpdate(String userRoleId, String parentRoleId) {
		this.userRoleId = userRoleId;
		this.parentRoleId = parentRoleId;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
		selectStmt.setString(1, parentRoleId);
		ResultSet rs = selectStmt.executeQuery();
		if (rs.next()) {
			Long id = rs.getLong(1);
			PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
			updateStmt.setLong(1, id);
			updateStmt.setString(2, userRoleId);
			updateStmt.executeUpdate();
			updateStmt.close();
			rs.close();
		}
		selectStmt.close();
	}
}