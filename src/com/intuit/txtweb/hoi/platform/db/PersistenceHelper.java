package com.intuit.txtweb.hoi.platform.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author mkurian
 * 
 */
public class PersistenceHelper {

	private static Logger logger = Logger.getLogger(PersistenceHelper.class
			.getName());

	private static Connection getConnection() throws SQLException,
			ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw ex;
		}
		Connection connection = null;
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/hoi", "hoi_app", "hoi_pwd");
		logger.log(Level.INFO, "Connection to DB established");
		return connection;
	}

	public static int createGroupAddAdmin(String groupName, String userName,
			String mobileHash) throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement stmt = null;
		int result;
		try {
			con = getConnection();
			stmt = con.createStatement();

			String createGroup = "INSERT INTO hoi.hoi_group (group_name) VALUES('"
					+ groupName + "')";
			result = stmt.executeUpdate(createGroup);

			String addAdmin = "INSERT INTO hoi.hoi_user (group_id, user_name, mobile_hash, is_registered, is_admin) "
					+ "SELECT group_id, '"
					+ userName
					+ "', '"
					+ mobileHash
					+ "', 1, 1 from hoi.hoi_group where group_name like'"
					+ groupName + "'";
			result = stmt.executeUpdate(addAdmin);

			logger.log(Level.INFO, "Created group and inserted admin user");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	
	public static String readMessageFromGroup(String groupName) throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement stmt = null;
		String result = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			ResultSet rs = null;
			String readMessage = "SELECT message from hoi.hoi_group where group_id in "
					+ "(select group_id from hoi.hoi_group where group_name like '"
					+ groupName + "') order by message_id desc limit 1;";
			rs = stmt.executeQuery(readMessage);
			while (rs.next()) {
				result = rs.getString("message");
				}			
			logger.log(Level.INFO, "Read message from group");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	
	public static int registerUserToGroup(String groupName, String userName,
			String mobileHash) throws SQLException, ClassNotFoundException { 
		Connection con = null;
		Statement stmt = null;
		int result;
		try {
			con = getConnection();
			stmt = con.createStatement();

			String registerUser = "INSERT INTO hoi.hoi_user (group_id, user_name, mobile_hash, is_registered, is_admin) "
					+ "SELECT group_id, '"
					+ userName
					+ "', '"
					+ mobileHash
					+ "', 1, 0 from hoi.hoi_group where group_name like'"
					+ groupName + "'";
			result = stmt.executeUpdate(registerUser);
			logger.log(Level.INFO, "Registered user to group");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}
	
	public static int logMessageToGroup(String groupName, String message,
			String mobileHash) throws SQLException, ClassNotFoundException { 
		Connection con = null;
		Statement stmt = null;
		int result;
		try {
			con = getConnection();
			stmt = con.createStatement();

			String registerUser = "INSERT INTO hoi.hoi_message (message, group_id) "
					+ "SELECT '" + message	+ "', group_id from hoi.hoi_group where group_name like'" + groupName + "'";
			result = stmt.executeUpdate(registerUser);
			logger.log(Level.INFO, "Registered user to group");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	public static int unregisterUserFromGroup(String groupName,
			String userName, String mobileHash)  throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement stmt = null;
		int result;
		try {
			con = getConnection();
			stmt = con.createStatement();

			String registerUser = "UPDATE hoi.hoi_user "
					+ "SET is_registered = 0, user_unregistered = now()"
					+ " where mobile_hash = '"
					+ mobileHash
					+ "' and group_id = "
					+ "(SELECT group_id from hoi.hoi_group where group_name like'"
					+ groupName + "')";
			result = stmt.executeUpdate(registerUser);
			logger.log(Level.INFO, "Un-Registered user from group");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	public static Collection<String> getUsersOfGroup(String groupName,
			String userName, String mobileHash)  throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement stmt = null;
		Set<String> registeredUsers = new HashSet<String>();
		ResultSet rs = null;
		try {
			con = getConnection();
			stmt = con.createStatement();

			String registerUser = "SELECT mobile_hash from hoi.hoi_user where group_id in "
					+ "(select group_id from hoi.hoi_group where group_name like '"
					+ groupName + "') and is_registered = 1";
			rs = stmt.executeQuery(registerUser);

			while (rs.next()) {
				registeredUsers.add(rs.getString("mobile_hash"));
			}
			logger.log(Level.INFO, "Get users of group to send messages");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return registeredUsers;
	}

	public static boolean canUserSendMessages(String groupName,
			String userName, String mobileHash)  throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement stmt = null;
		boolean hasPermissions = false;
		ResultSet rs = null;
		try {
			con = getConnection();
			stmt = con.createStatement();

			String registerUser = "SELECT is_admin from hoi.hoi_user where group_id in "
					+ "(select group_id from hoi.hoi_group where group_name like '"
					+ groupName
					+ "') and mobile_hash like '"
					+ mobileHash
					+ "'";
			rs = stmt.executeQuery(registerUser);

			while (rs.next()) {
				if (rs.getInt("is_admin") == 1) {
					hasPermissions = true;
					break;
				}
			}
			logger.log(Level.INFO, "Can user send messages to group");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return hasPermissions;
	}
}