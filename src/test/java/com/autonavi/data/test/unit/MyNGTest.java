package com.autonavi.data.test.unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;
import com.autonavi.data.test.POIDataFlowPackage;
import com.autonavi.data.test.POIDataItem;
import com.autonavi.data.test.POIDataItemBase;
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
				.deserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataFlowPackages/后期生成20150623-03.dat");
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
	
}
