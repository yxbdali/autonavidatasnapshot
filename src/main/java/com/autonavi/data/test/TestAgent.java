/**
 * 
 */
package com.autonavi.data.test;

import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.sound.midi.SysexMessage;

import oracle.spatial.geometry.JGeometry;
import javafx.scene.chart.ValueAxis;

/**
 * @author xiangbin.yang
 *
 */
public class TestAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// testGetPDAPOIDataSnapshot();
		// testSerialize();
		//testGetPDAPOIDataSnapshotList();
		//testDeserialzePDAPOIDataSnapshotList();
		//testTaskPackage();
		//testDataFlowPackage();
		//testDbQueryConfig();
		//testXtreamDeserialize();
		//testDbQueryHelper();
		//listPOIDataType();
		//testSqlBuilder3();
		//testRollback();
		//testsqlupdate();
		//testDataFlowPackageData();
		//testTbd_data_flowSerializeData();
		//testBuildSqlInsert();
		//testRollbackInsert();
		//testDeserializeComments();
		testDbConfigItem();
		//testDbConfigList();
	}

	private static void testSerialize() {

		try {
			PDAPOIDataItem pdapoiDataItem = new PDAPOIDataItem();
			pdapoiDataItem.getPDAPOIDataItem("FF8CDC32C73C9333E040007F01000B05");
			Set<String> keys = pdapoiDataItem.getDataHashMap().keySet();
			for (String key : keys) {
				System.out.printf("%s, %s\n", key, pdapoiDataItem.getDataHashMap().get(key));
			}
			pdapoiDataItem.save("E:\\tempdata\\POIDBDataSnapshot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testDeserialize() throws ClassNotFoundException, IOException {
		PDAPOIDataItem pdapoiDataItem = (PDAPOIDataItem) PDAPOIDataItem
				.deserialize("E:/tempdata/POIDBDataSnapshot/2015-04-22_18-48-23/FF8CDC3EC5159333E040007F01000B05.dat");
		Set<String> keys = pdapoiDataItem.getDataHashMap().keySet();
		for (String key : keys) {
			System.out.printf("%s, %s\n", key, pdapoiDataItem.getDataHashMap().get(key));
		}
		System.out.printf("Save time: %s", pdapoiDataItem.getSaveDate());
	}

	private static void testGetPDAPOIDataSnapshot() {
		PDADataSnapshot pdaDataSnapshot = new PDADataSnapshot();
		pdaDataSnapshot.getPDADataSnapshotData("FF8CDC32C73C9333E040007F01000B05");
		try {
			pdaDataSnapshot.save("E:\\tempdata\\POIDBDataSnapshot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testGetPDAPOIDataSnapshotList() {
		PDADataSnapshotList pdaDataSnapshotList = new PDADataSnapshotList();
		PDADataSnapshot pdaDataSnapshot = new PDADataSnapshot();
		pdaDataSnapshot.getPDADataSnapshotData("FF8CDC32C73C9333E040007F01000B05");

		pdaDataSnapshotList.add(pdaDataSnapshot);
		try {
			pdaDataSnapshotList.save("E:\\tempdata\\POIDBDataSnapshot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finished!");
	}

	private static void testDeserialzePDAPOIDataSnapshotList() {
		PDADataSnapshotList pdaDataSnapshotList = null;
		try {
			pdaDataSnapshotList = PDADataSnapshotList
					.deserialze("E:\\tempdata\\POIDBDataSnapshot\\2015-04-24_11-11-30\\FF8CDC32C73C9333E040007F01000B05.dat");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pdaDataSnapshotList != null) {
			for (PDADataSnapshot pdaDataSnapshot : pdaDataSnapshotList) {
				PDAPOIDataItem pdapoiDataItem = pdaDataSnapshot.getPDAPOISnapshot();
				Set<String> keys = pdapoiDataItem.getDataHashMap().keySet();
				for (String key : keys) {
					System.out.printf("%s, %s\n", key, pdapoiDataItem.getDataHashMap().get(key));
				}
				System.out.println("----------------");
				
				PDAPOIDataFlowItem pdapoiDataFlowItem = pdaDataSnapshot.getPDAPOIDataFlowSnapshot();
				Set<String> keys2 = pdapoiDataFlowItem.getDataHashMap().keySet();
				for(String key : keys2){
					System.out.printf("%s, %s\n", key, pdapoiDataFlowItem.getDataHashMap().get(key));
				}
			}
		}
	}
	
	private static void testTaskPackage(){
		TaskPackage taskPackage;
		try {
			taskPackage = new TaskPackage("E459E17EF3B04E2DA248E9F6C1588006", "This is a task test package", "编辑");
			taskPackage.save("E:\\tempdata\\POIDBDataSnapshot\\taskpackage.data");
			System.out.println("Done!");
		} catch (SQLException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private static void testDataFlowPackage(){
		DataFlowPackage dataFlowPackage = new DataFlowPackage();
		dataFlowPackage.setCreateDate(new Date());
		dataFlowPackage.setPackageName("这是一个测试数据流包");
		
		TaskPackage innerTaskDataItem = null;
		try {
			innerTaskDataItem = new TaskPackage("E459E17EF3B04E2DA248E9F6C1588006");
			//innerTaskDataItem.getInnerTaskDataItem();
			//taskPackage = new TaskPackage("E459E17EF3B04E2DA248E9F6C1588006", "This is a task test package", "编辑");
			//taskPackage.save("E:\\tempdata\\POIDBDataSnapshot\\taskpackage.dat");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		dataFlowPackage.add(innerTaskDataItem);
		dataFlowPackage.serialize("E:\\tempdata\\POIDBDataSnapshot\\dataflowpackage.dat");
		System.out.println("Done!");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private static void testDbQueryConfig(){
		//DbTableQueryConfigItem aConfigItem = new DbTableQueryConfigItem();
		DbTableQueryConfigItem poiTableQueryConfigItem = new DbTableQueryConfigItem("MDB_POI_EDIT", 
				"WHERE mdb_poi_edit.guid in (SELECT feature_id FROM tdb_data_flow WHERE task_id = '[TASKID]')");
		ArrayList<String> mdbPoiEditTableWhereClauseList = new ArrayList<>();
		mdbPoiEditTableWhereClauseList.add("GUID");
		poiTableQueryConfigItem.setRollbackQueryClauseFieldList(mdbPoiEditTableWhereClauseList);
		
		ArrayList<String> poiViewColumnList = new ArrayList<>();
		poiViewColumnList.add("GUID");
		poiViewColumnList.add("NAME");
		poiTableQueryConfigItem.setViewColumnList(poiViewColumnList);
		
		DbTableQueryConfigItem dataFlowQueryConfigItem = new DbTableQueryConfigItem("tdb_data_flow", 
				"WHERE task_id = '[TASKID]'");
		ArrayList<String> tdbDataFlowTableWhereClauseList = new ArrayList<>();
		tdbDataFlowTableWhereClauseList.add("DATA_FLOW_ID");
		dataFlowQueryConfigItem.setRollbackQueryClauseFieldList(tdbDataFlowTableWhereClauseList);
		
		ArrayList<String> dataFlowInsertExcludeColumnList = new ArrayList<>();
		dataFlowInsertExcludeColumnList.add("DATA_FLOW_ID");
		dataFlowQueryConfigItem.setInsertExcludeColumnList(dataFlowInsertExcludeColumnList);
		
		ArrayList<String> dataFlowViewColumnList = new ArrayList<>();
		dataFlowViewColumnList.add("DATA_FlOW_ID");
		dataFlowViewColumnList.add("MATERIAL_ID");
		dataFlowViewColumnList.add("DATA_FLOW_TYPE");
		dataFlowQueryConfigItem.setViewColumnList(dataFlowViewColumnList);
		
		ArrayList<DbTableQueryConfigItem> dbTableQueryConfigList = new ArrayList<>();
		dbTableQueryConfigList.add(poiTableQueryConfigItem);
		dbTableQueryConfigList.add(dataFlowQueryConfigItem);

		DbTaskQueryConfigItem dbTaskQueryConfigItem = new DbTaskQueryConfigItem("业内生产", "tdb_inner_task", "WHERE TASK_ID = '[TASKID]'", dbTableQueryConfigList);
		
		ArrayList<DbTaskQueryConfigItem> dbTaskQueryConfigList = new ArrayList<>();
		dbTaskQueryConfigList.add(dbTaskQueryConfigItem);
		
		DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = new DbDataFlowQueryConfigItem(dbTaskQueryConfigList);
		dbDataFlowQueryConfigItem.setDataFlowType("POI");
		
		try {
			dbDataFlowQueryConfigItem.serialize("E:/tempdata/POIDBDataSnapshot/DataFlowQuery.xml");
			dbDataFlowQueryConfigItem.xstreamSerialize("E:/tempdata/POIDBDataSnapshot/DataFlowQuery1.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testDbQueryHelper(){
		try {
			DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.deserialize("E:/tempdata/POIDBDataSnapshot/DataFlowQuery.xml");
			DbTaskQueryConfigItem taskQueryConfigItem = dbDataFlowQueryConfigItem.getTaskDataQueryConfigList().get(0);
			DbQueryHelper queryHelper = new DbQueryHelper(taskQueryConfigItem, "1A245C745F89458AAF56F098BE25827E");
			POITaskPackage poiTaskPackage = queryHelper.query();
			ArrayList<POITaskPackage> poiTaskPackageList = new ArrayList<>();
			poiTaskPackageList.add(poiTaskPackage);
			POIDataFlowPackage poiDataFlowPackage = new POIDataFlowPackage("This is a test data flow package");
			poiDataFlowPackage.setTaskPackageList(poiTaskPackageList);
			poiDataFlowPackage.serialize("E:/tempdata/POIDBDataSnapshot/DataFlowPackage1.dat");
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void listPOIDataType(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:\\NetBeans_Projects\\AutonaviDataFlowSnapshotUI\\DataFlowPackages\\chargepile1.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(2);
			ArrayList<POIDataItemBase> poiDataList = poiTaskPackage.getDbDataList();
			POIDataItemBase poiDataItem = poiDataList.get(poiDataList.size() - 1);
			HashMap<String, Object> dataMap = poiDataItem.getDataMap();
			Set<String> keys = dataMap.keySet();
			System.out.println(keys.toArray().length);
			for (String key : keys) {
				Object val = dataMap.get(key);
				if (val != null){
				String type = val.getClass().getName();
				
				System.out.printf("Column: %s, Value: %s, Type: %s\n", key, val, type);
				}
				if (key.equalsIgnoreCase("GEOM")){
					System.out.println(((JGeometry)val).toString());
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testSqlBuilder(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:/tempdata/POIDBDataSnapshot/DataFlowPackage1.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			ArrayList<POIDataItem> poiDataList = poiTaskPackage.getPOIDataList();
			POIDataItem poiDataItem = poiDataList.get(0);
			HashMap<String, Object> dataMap = poiDataItem.getDataMap();
			System.out.println(SqlBuilder.buildUpdateSql(poiDataItem.getTableName(), dataMap, "GUID", poiDataItem.getPOIId()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testSqlBuilder2(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:/tempdata/POIDBDataSnapshot/DataFlowPackage1.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			
			HashMap<String, Object> dataMap = poiTaskPackage.getDataMap();
			System.out.println(SqlBuilder.buildUpdateSql(poiTaskPackage.getTableName(), dataMap, "TASK_ID", poiTaskPackage.getTaskId()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testSqlBuilder3(){
		try {
			DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.deserialize("E:/tempdata/POIDBDataSnapshot/DataFlowQuery.xml");
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:/tempdata/POIDBDataSnapshot/DataFlowPackage1.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			
			ArrayList<POIDataItemBase> dataList = poiTaskPackage.getDbDataList();
			POIDataItemBase dataItem = dataList.get(0);
			HashMap<String, Object> dataMap = dataItem.getDataMap();
			DbTableQueryConfigItem dbTableQueryConfigItem = RollbackHelper.findDbTableQueryConfigItem(dbDataFlowQueryConfigItem, dataItem.getTableName());
			ArrayList<String> rollbackClauseList = dbTableQueryConfigItem.getRollbackQueryClauseFieldList();
			HashMap<String, Object> clauseDataMap = new HashMap<String, Object>();
			for (String rollbackClause : rollbackClauseList) {
				Object clauseValue = dataMap.get(rollbackClause);
				clauseDataMap.put(rollbackClause, clauseValue);
			}
			String sql = SqlBuilder.buildUpdateSql(dataItem.getTableName(), dataMap, rollbackClauseList, clauseDataMap);
			System.out.println(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testRollback(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:\\NetBeans_Projects\\AutonaviDataFlowSnapshotUI\\DataFlowPackages\\test2.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			//RollbackHelper.rollback(poiTaskPackage);
			System.out.println("Pass");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testsqlupdate(){
		try(DBDriver dbDriver = new DBDriver()){
			dbDriver.executeSql("UPDATE tdb_inner_task SET TASK_NAME='内业生产42651',USER_ID='7265F574B7B6445498306293D225D4B4',OPERATE_TIME=to_timestamp('2014-12-29 17:53:56', 'yyyy-mm-dd hh24:mi:ss:ff') WHERE TASK_ID='3117DDC00FDC417596244A273F51E771'");
			System.out.println("Pass");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testDataFlowPackageData(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:\\NetBeans_Projects\\AutonaviDataFlowSnapshotUI\\DataFlowPackages\\a.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			ArrayList<POIDataItemBase> dataList = poiTaskPackage.getDbDataList();
			for (POIDataItemBase dataItem : dataList) {
				String tableName = dataItem.getTableName();
				System.out.println(tableName);
				HashMap<String, Object> dataMap = dataItem.getDataMap();
				Set<String> keySet = dataMap.keySet();
				for (String key : keySet) {
					System.out.printf("%s: %s\n", key, dataMap.get(key));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void testTbd_data_flowSerializeData(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:\\NetBeans_Projects\\AutonaviDataFlowSnapshotUI\\DataFlowPackages\\bug7.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			ArrayList<POIDataItemBase> dataList = poiTaskPackage.getDbDataList();
			for(POIDataItemBase dataItem : dataList){
				String tableNameString = dataItem.getTableName();
				if (tableNameString.equalsIgnoreCase("tdb_inner_user_package")){
					HashMap<String, Object> dataMap = dataItem.getDataMap();
					BigDecimal dataFlowId = (BigDecimal)dataMap.get("PACKAGE_ID");
					//BigDecimal processType = (BigDecimal)dataMap.get("PROCESS_TYPE");
					System.out.printf("%s\n", dataFlowId);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testXtreamDeserialize(){
		try {
			DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.xstreamDeserialize("E:/tempdata/POIDBDataSnapshot/DataFlowQuery1.xml");
			System.out.println("Pass");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testBuildSqlInsert(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:\\NetBeans_Projects\\AutonaviDataFlowSnapshotUI\\DataFlowPackages\\bug8.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			ArrayList<POIDataItem> dataList = poiTaskPackage.getPOIDataList();
			for (POIDataItem poiDataItem : dataList) {
				String tableName = poiDataItem.getTableName();
				String sql = SqlBuilder.buildInsertSql(tableName, poiDataItem.getDataMap());
				System.out.println(sql);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testRollbackInsert(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("E:\\NetBeans_Projects\\AutonaviDataFlowSnapshotUI\\DataFlowPackages\\chargepile1.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(2);
			DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.xstreamDeserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataQueries/CHARGEPILE.xml");
			RollbackHelper.rollback(dbDataFlowQueryConfigItem, poiTaskPackage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void testDeserializeComments(){
		try {
			POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize("C:/Users/xiangbin.yang/Documents/我接收到的文件/ 外包库.dat");
			POITaskPackage poiTaskPackage = poiDataFlowPackage.getTaskPackageList().get(0);
			ArrayList<POIDataItemBase> dataList = poiTaskPackage.getDbDataList();
			for (POIDataItemBase item : dataList) {
				if (item.getTableName().equalsIgnoreCase("tdb_data_flow")){
					System.out.printf("%s, %s\n", item.getDataMap().get("MATERIAL_ID"), item.getDataMap().get("FEATURE_CODE"));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void testDbConfigItem() {
		DbConfigItem dbConfigItem = new DbConfigItem("Db Config 1", "10.17.129.217", "1521", "orcl", "USER_OUT_V108", "testgdpoi");
		try {
			dbConfigItem.serialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DbConfig.xml");
			System.out.println("Pass");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
