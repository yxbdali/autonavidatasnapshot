/**
 * 
 */
package com.autonavi.data.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.management.OperationsException;

import oracle.spatial.geometry.JGeometry;

/**
 * @author xiangbin.yang
 *
 */
public class RollbackHelper {

	/**
	 * Rollback POITask with default settings
	 * 
	 * @param dbDataFlowQueryConfigItem
	 * @param poiTaskPackage
	 * @throws SQLException
	 * @throws OperationsException
	 */
	public static void rollback(DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem, POITaskPackage poiTaskPackage)
			throws OperationsException, SQLException {
		rollback(dbDataFlowQueryConfigItem, poiTaskPackage, null, null, null);
	}

	/**
	 * Rollback POITask with given connection, user and password
	 * 
	 * @param dbDataFlowQueryConfigItem
	 * @param poiTaskPackage
	 * @param connection
	 * @param user
	 * @param password
	 * @throws OperationsException
	 * @throws SQLException
	 */
	public static void rollback(DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem, POITaskPackage poiTaskPackage,
			String connection, String user, String password) throws OperationsException, SQLException {
		String taskTableName = poiTaskPackage.getTableName();
		String taskId = poiTaskPackage.getTaskId();
		HashMap<String, Object> taskDataMap = poiTaskPackage.getDataMap();

		String taskRollbackSql = SqlBuilder.buildUpdateSql(taskTableName, taskDataMap, "TASK_ID", taskId);
		String taskRollbackSqlInsert = SqlBuilder.buildInsertSql(taskTableName, taskDataMap);
		SqlBundle taskRollbackSqlBundle = new SqlBundle(taskRollbackSql, taskRollbackSqlInsert, "", null);
		try (DBDriver dbDriver = new DBDriver(connection, user, password)) {
			// rollback task;
			rollbackTask(dbDriver, taskRollbackSqlBundle, dbDataFlowQueryConfigItem, poiTaskPackage);
		}

	}

	private static void rollbackTask(DBDriver dbDriver, SqlBundle taskRollbackSqlBundle,
			DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem, POITaskPackage poiTaskPackage) throws SQLException {
		if (dbDriver.executeSql(taskRollbackSqlBundle.getSqlUpdate()) == 0) {
			dbDriver.executeSql(taskRollbackSqlBundle.getSqlInsert());
		}

		ArrayList<POIDataItem> poiDataList = poiTaskPackage.getPOIDataList();
		ArrayList<SqlBundle<JGeometry>> poiRollbackList = new ArrayList<>();
		for (POIDataItem poiDataItem : poiDataList) {
			String poiTableName = poiDataItem.getTableName();
			String poiGuid = poiDataItem.getPOIId();
			HashMap<String, Object> poiDataMap = poiDataItem.getDataMap();

			String poiRollbackSqlUpdate = SqlBuilder.buildUpdateSql(poiTableName, poiDataMap, "GUID", poiGuid);
			String poiRollbackSqlInsert = SqlBuilder.buildInsertSql(poiTableName, poiDataMap);
			String poiRollbackSqlSelect = "";
			JGeometry jGeometry = (JGeometry) poiDataMap.get("GEOM");
			poiRollbackList.add(new SqlBundle(poiRollbackSqlUpdate, poiRollbackSqlInsert, poiRollbackSqlSelect,
					jGeometry));
		}

		for (SqlBundle sqlBundle : poiRollbackList) {
			// rollback poi
			if (dbDriver.executeSql(sqlBundle.getSqlUpdate()) == 0) {
				// if rollback poi as update the existed one failed that means
				// ,then insert the snapshot back to database
				dbDriver.executeSqlWithJGeometryObj(sqlBundle.getSqlInsert(), (JGeometry) sqlBundle.getObj());
			}
		}
		System.out.println("Rollback poi data done!");

		ArrayList<POIDataItemBase> dataList = poiTaskPackage.getDbDataList();
		ArrayList<SqlBundle<Object>> rollbackSqlList = new ArrayList<>();
		ArrayList<SqlBundle<JGeometry>> geomRollbackSqlList = new ArrayList<>();
		for (POIDataItemBase poiDataItemBase : dataList) {
			String tableName = poiDataItemBase.getTableName();
			HashMap<String, Object> dataMap = poiDataItemBase.getDataMap();
			DbTableQueryConfigItem dbTableQueryConfigItem = findDbTableQueryConfigItem(dbDataFlowQueryConfigItem,
					tableName);
			if (dbTableQueryConfigItem != null) {
				ArrayList<String> rollbackClauseList = dbTableQueryConfigItem.getRollbackQueryClauseFieldList();

				HashMap<String, Object> clauseDataMap = new HashMap<String, Object>();
				for (String rollbackClause : rollbackClauseList) {
					Object clauseValue = dataMap.get(rollbackClause);
					clauseDataMap.put(rollbackClause, clauseValue);
				}
				String sqlUpdate = SqlBuilder.buildUpdateSql(tableName, dataMap, rollbackClauseList, clauseDataMap);
				String sqlInsert = SqlBuilder.buildInsertSql(tableName, dataMap);
				String sqlSelect = "";
				if (!dataMap.keySet().contains("GEOM")){
					rollbackSqlList.add(new SqlBundle<Object>(sqlUpdate, sqlInsert, sqlSelect, null));
				}
				else {
					Object geomObj = dataMap.get("GEOM");
					JGeometry jGeometry = null;
					if (geomObj != null){
						jGeometry = (JGeometry)geomObj;
					}
					geomRollbackSqlList.add(new SqlBundle<JGeometry>(sqlUpdate, sqlInsert, sqlSelect, jGeometry));
				}
			}
		}

		for (SqlBundle sqlBundle : rollbackSqlList) {
			if (dbDriver.executeSql(sqlBundle.getSqlUpdate()) == 0) {
				dbDriver.executeSql(sqlBundle.getSqlInsert());
			}
		}
		for (SqlBundle<JGeometry> sqlBundle : geomRollbackSqlList) {
			if (dbDriver.executeSql(sqlBundle.getSqlUpdate()) == 0){
				dbDriver.executeSqlWithJGeometryObj(sqlBundle.getSqlInsert(), (JGeometry) sqlBundle.getObj());
			}
		}
		System.out.println("Rollback other data done!");
	}

	/**
	 * Find Data base query config item from Data base data flow query config
	 * 
	 * @param dbDataFlowQueryConfigItem
	 * @param tableName
	 * @return
	 */
	public static DbTableQueryConfigItem findDbTableQueryConfigItem(
			DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem, String tableName) {
		DbTableQueryConfigItem dbTableQueryConfigItem = null;

		for (DbTableQueryConfigItem _dbTableQueryConfigItem : dbDataFlowQueryConfigItem.getTaskDataQueryConfigList()
				.get(0).getDbTableQueryConfigList()) {
			if (_dbTableQueryConfigItem.getTableName().equalsIgnoreCase(tableName)) {
				dbTableQueryConfigItem = _dbTableQueryConfigItem;
				break;
			}
		}

		return dbTableQueryConfigItem;
	}

}
