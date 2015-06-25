/**
 * 
 */
package com.autonavi.data.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangbin.yang
 *
 */
public class DbQueryHelper {
	private DbTaskQueryConfigItem taskQueryConfigItem;
	private String taskId;
	private String dbConfigName;

	/**
	 * Constructor
	 * 
	 * @param taskQueryConfigItem
	 * @param taskId
	 */
	public DbQueryHelper(DbTaskQueryConfigItem taskQueryConfigItem, String taskId) {
		// TODO Auto-generated constructor stub
		this.taskQueryConfigItem = taskQueryConfigItem;
		this.taskId = taskId;
	}

	/**
	 * Constructor
	 * 
	 * @param taskQueryConfigItem
	 * @param taskId
	 * @param dbConfigName
	 */
	public DbQueryHelper(DbTaskQueryConfigItem taskQueryConfigItem, String taskId, String dbConfigName) {
		// TODO Auto-generated constructor stub
		this(taskQueryConfigItem, taskId);
		this.dbConfigName = dbConfigName;
	}

	public POITaskPackage query() throws SQLException {
		return query(null, null, null);
	}

	public POITaskPackage query(String connection, String user, String password) throws SQLException {
		POITaskPackage poiTaskPackage = null;
		// ArrayList<POIDataPackage> poiDataPackageList = new ArrayList<>();

		String taskTableName = taskQueryConfigItem.getTableName();
		String taskQueryClause = taskQueryConfigItem.getClause();
		String taskName = "";

		Map<String, Object> taskDataMap = null;

		try (DBDriver dbDriver = new DBDriver(connection, user, password)) {
			String sql = String.format("SELECT * FROM %s %s", taskTableName, taskQueryClause).replace("[TASKID]",
					taskId);
			taskDataMap = dbDriver.getFirstDataSet(sql);
			taskName = (String) taskDataMap.get("TASK_NAME");
		}

		ArrayList<POIDataItem> poiDataList = new ArrayList<>();
		ArrayList<POIDataItemBase> dbDataList = new ArrayList<>();
		for (DbTableQueryConfigItem dbTableQueryConfigItem : taskQueryConfigItem.getDbTableQueryConfigList()) {

			String tableName = dbTableQueryConfigItem.getTableName();
			String sql = String.format("SELECT * FROM %s %s", tableName, dbTableQueryConfigItem.getClause()).replace(
					"[TASKID]", taskId);
			try (DBDriver dbDriver = new DBDriver(connection, user, password)) {
				List<Map<String, Object>> dataMapList = dbDriver.getDataSetList(sql);
				if (tableName.equalsIgnoreCase("mdb_poi_edit")) {
					for (Map<String, Object> dataMap : dataMapList) {

						String poiId = (String) dataMap.get("GUID");
						String chnName = (String) dataMap.get("NAME");
						POIDataItem poiDataItem = new POIDataItem(poiId, chnName, dataMap);
						poiDataItem.setTableName(tableName);

						poiDataList.add(poiDataItem);

					}
				} else {
					for (Map<String, Object> dataMap : dataMapList) {
						POIDataItemBase dbDataItem = new POIDataItemBase();
						dbDataItem.setTableName(tableName);
						dbDataItem.setDataMap(dataMap);

						dbDataList.add(dbDataItem);
					}
				}
			}
		}

		poiTaskPackage = new POITaskPackage(taskId, taskName, taskTableName, dbConfigName, taskDataMap, poiDataList,
				dbDataList);

		return poiTaskPackage;
	}
}
