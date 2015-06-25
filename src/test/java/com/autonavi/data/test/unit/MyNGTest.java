package com.autonavi.data.test.unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;

import com.autonavi.data.test.DbDataFlowQueryConfigItem;
import com.autonavi.data.test.DbTableQueryConfigItem;
import com.autonavi.data.test.POIDataFlowPackage;
import com.autonavi.data.test.POIDataItem;
import com.autonavi.data.test.POIDataItemBase;
import com.autonavi.data.test.RollbackHelper;
import com.autonavi.data.test.SqlBuilder;
/**
 * 
 * @author xiangbin.yang
 *
 */
public class MyNGTest {
	static org.apache.logging.log4j.Logger log = LogManager.getLogger();

	
	@Test
	public void testSpecialAchrieveData() throws ClassNotFoundException, IOException {
		POIDataFlowPackage package1 = POIDataFlowPackage
				.deserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataFlowPackages/后期1112.dat");
		ArrayList<POIDataItemBase> dbDataList = package1.getTaskPackageList().get(0).getDbDataList();
		Map<String, Integer> tableDataCountMap = new HashMap<String, Integer>();
		for (POIDataItemBase item : dbDataList) {
			String dbTableName = item.getTableName();
			if (!tableDataCountMap.containsKey(dbTableName)) {
				tableDataCountMap.put(dbTableName, 1);
			} else {
				int _count = tableDataCountMap.get(dbTableName);
				tableDataCountMap.put(dbTableName, ++_count);
			}
		}
		log.info(String.format("Total data count: %d", dbDataList.size()));

		Set<String> keySet = tableDataCountMap.keySet();
		for (String key : keySet) {
			log.info(String.format("Table: %s, DataCount: %d", key, tableDataCountMap.get(key)));
		}
		List<POIDataItem> poiDataList = package1.getTaskPackageList().get(0).getPOIDataList();
		log.info(String.format("Table: %s, DataCount %d", "MDB_POI_Edit", poiDataList.size()));
	}
	
	
	@Test
	public void testSqlBulder() throws ClassNotFoundException, IOException{
		POIDataFlowPackage package1 = POIDataFlowPackage
				.deserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataFlowPackages/后期测试1.dat");
		DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.xstreamDeserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataQueries/PostLine.xml");
		ArrayList<POIDataItem> poiDataList = package1.getTaskPackageList().get(0).getPOIDataList();
		for (POIDataItem poiDataItem : poiDataList) {
			HashMap<String, Object> poiDataMap = poiDataItem.getDataMap();
			String poiTableName = poiDataItem.getTableName();
			DbTableQueryConfigItem poiEditDbTableQueryConfigItem = RollbackHelper.findDbTableQueryConfigItem(dbDataFlowQueryConfigItem, poiTableName);
			ArrayList<String> rollbackClauseList = poiEditDbTableQueryConfigItem.getRollbackQueryClauseFieldList();
			HashMap<String, Object> clauseDataMap = new HashMap<>();
			for (String rollbackClause : rollbackClauseList) {
				Object clauseValue = poiDataMap.get(rollbackClause);
				clauseDataMap.put(rollbackClause, clauseValue);
			}
			
			String sql = SqlBuilder.buildUpdateSql(poiDataItem.getTableName(), poiDataMap,  rollbackClauseList, clauseDataMap);
			if (poiTableName.equalsIgnoreCase("mdb_poi_edit")){
				log.info(sql);
			}
		}
	}
}
