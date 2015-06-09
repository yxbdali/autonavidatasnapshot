/**
 * 
 */
package com.autonavi.data.test;

import java.beans.Statement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

/**
 * @author xiangbin.yang
 *
 */
public class DBDriver implements AutoCloseable {
	private String defaultDbConnString = "jdbc:oracle:thin:@10.17.129.217:1521:orcl";
	private String defaultDbUser = "USER_PTMP_V108";
	private String defaultDbPassword = "testgdpoi";

	private Connection dbConnection;
	private java.sql.Statement statement;

	/**
	 * Default constructor
	 * 
	 * @throws SQLException
	 */
	public DBDriver() throws SQLException {
		// TODO Auto-generated constructor stub
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		//iniDbConnInfoFromConfig();
		dbConnection = DriverManager.getConnection(defaultDbConnString, defaultDbUser, defaultDbPassword);
	}

	/**
	 * Constructor with given connection string, user and password
	 * 
	 * @param connstring
	 * @param user
	 * @param password
	 * @throws SQLException
	 */
	public DBDriver(String connstring, String user, String password) throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		defaultDbConnString = connstring == null ? defaultDbConnString : connstring;
		defaultDbUser = user == null ? defaultDbUser : user;
		defaultDbPassword = password == null ? defaultDbPassword : password;
		dbConnection = DriverManager.getConnection(defaultDbConnString, defaultDbUser, defaultDbPassword);;
	}

	public void getPOIName(String sql) throws SQLException {
		ResultSet resultSet = getRecords(sql);
		while (resultSet.next()) {
			System.out.printf("POI Name: %s\n", resultSet.getString("NAME"));
		}
		resultSet.close();
	}

	public void showDbTableMeta(String sql) throws SQLException {
		ResultSet resultSet = getRecords(sql);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			String columnLabel = resultSetMetaData.getColumnLabel(i);
			String columnTypeName = resultSetMetaData.getColumnTypeName(i);
			System.out.printf("%s		%s\n", columnLabel, columnTypeName);
		}
	}

	/**
	 * Get ResultSet from given sql query
	 * 
	 * @param sqlquery
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getRecords(String sqlquery) throws SQLException {
		try {
			statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlquery);
			return resultSet;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new SQLException(String.format("Execute '%s' failed\n%s", sqlquery, e.toString()));
		}
		
	}

	/**
	 * Execute a sql
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String sql) throws SQLException {
		try {
			int result = 0;
			try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
				result = pstmt.executeUpdate();
			}
			return result;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new SQLException(String.format("Execute the sql '%s' failed, the reason is: %s", sql, e.getMessage()));
		}
		
	}
	
	public void executeSqlWithJGeometryObj(String sql, JGeometry jgeo) throws SQLException{
		//int result = 0;
		try(PreparedStatement pstmt = dbConnection.prepareStatement(sql)){
			STRUCT geomStruct = JGeometry.store(jgeo, dbConnection);
			pstmt.setObject(1, geomStruct);
			pstmt.execute();
			//result = pstmt.executeUpdate(sql);
		}
		//return result;
	}

	public void showAllTables() throws SQLException {
		try (ResultSet resultSet = getRecords("select table_name from all_tables")) {
			while (resultSet.next()) {
				System.out.printf("%s\n", resultSet.getString(1));
			}
		}
	}

	/**
	 * Get first data set from given sql query
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Object> getFirstDataSet(String sql) throws SQLException {
		HashMap<String, Object> dataMap = new HashMap<>();

		try (ResultSet resultSet = getRecords(sql)) {
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int resultColumn = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= resultColumn; i++) {
					String columnName = resultSetMetaData.getColumnLabel(i);
					Object value = resultSet.getObject(i);
					if (columnName.equalsIgnoreCase("GEOM")) {
						// continue;
						STRUCT st = (STRUCT) value;
						JGeometry jGeometry = JGeometry.load(st);
						value = jGeometry;
					}
					dataMap.put(columnName, value);
				}
				break;
			}
		}

		return dataMap;
	}

	/**
	 * Get data set list from given sql query
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, Object>> getDataSetList(String sql) throws SQLException {
		ArrayList<HashMap<String, Object>> dataSetList = new ArrayList<>();

		try (ResultSet resultSet = getRecords(sql)) {
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int resultColumn = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				HashMap<String, Object> dataMap = new HashMap<>();
				for (int i = 1; i <= resultColumn; i++) {
					String columnName = resultSetMetaData.getColumnName(i);
					Object valueObject = resultSet.getObject(i);
					if (columnName.equalsIgnoreCase("GEOM") || columnName.equalsIgnoreCase("ORA_GEOMETRY")) {
						// continue;
						if (valueObject != null){
						STRUCT st = (STRUCT) valueObject;
						JGeometry jGeometry = JGeometry.load(st);
						valueObject = jGeometry;
						}
					}
					//} else {
						dataMap.put(columnName, valueObject);
					//}
				}
				dataSetList.add(dataMap);
			}
		}
		return dataSetList;
	}

	@Override
	public void close() throws SQLException {
		if (statement != null) {
			statement.close();
		}
		if (dbConnection != null) {
			// System.out.println("Open DB connection!");
			dbConnection.close();
			// System.out.println("Close DB connection!");
		}
	}
	
	private void iniDbConnInfoFromConfig(){
		String dbConfigFile = System.getProperty("user.dir") + File.separator + "DbConfig.xml";
		File dbConfigFileObj = new File(dbConfigFile);
		if (dbConfigFileObj.exists()){
			try {
				DbConfigItem dbConfigItem = DbConfigItem.deserialize(dbConfigFile);
				String server = dbConfigItem.getServer();
				String port = dbConfigItem.getPort();
				String sid = dbConfigItem.getSid();
				
				defaultDbConnString = String.format("jdbc:oracle:thin:@%s:%s:%s", server, port, sid);
				defaultDbUser = dbConfigItem.getUser();
				defaultDbPassword = dbConfigItem.getPassword();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
}
