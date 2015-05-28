/**
 * 
 */
package com.autonavi.data.test.unit;

import static org.junit.Assert.*;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sun.util.logging.resources.logging;

import com.autonavi.data.test.*;

/**
 * @author xiangbin.yang
 *
 */
public class UnitTest {
	static Logger log = LogManager.getLogger(UnitTest.class);
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDataAchievementCatch() throws IOException, ClassNotFoundException {
		POIDataFlowPackage package1 = POIDataFlowPackage.deserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataFlowPackages/成果表测试1.dat");
		POIDataFlowPackage package2 = POIDataFlowPackage.deserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataFlowPackages/成果表测试2.dat");	
		ArrayList<POIDataItemBase> dbDataList1 = package1.getTaskPackageList().get(0).getDbDataList();
		ArrayList<POIDataItemBase> achieveDataList1 = new ArrayList<>();
		for (POIDataItemBase dataItem : dbDataList1) {
			if (dataItem.getTableName().equalsIgnoreCase("mdb_poi") || dataItem.getTableName().equalsIgnoreCase("mdb_poi_chargepile")){
				achieveDataList1.add(dataItem);
			}
		}
		
		ArrayList<POIDataItemBase> dbDataList2 = package2.getTaskPackageList().get(0).getDbDataList();
		ArrayList<POIDataItemBase> achieveDataList2 = new ArrayList<>();
		for(POIDataItemBase dataItem : dbDataList2){
			if (dataItem.getTableName().equalsIgnoreCase("mdb_poi") || dataItem.getTableName().equalsIgnoreCase("mdb_poi_chargepile")){
				achieveDataList2.add(dataItem);
			}
		}
		
		log.info(String.format("package1 contains %d items", achieveDataList1.size()));
		log.info(String.format("package2 contains %d items", achieveDataList2.size()));
		//assertEquals(achieveDataList1.size(), achieveDataList2.size());
	}
	
	@Test
	public void testDataAchieveCatch() throws FileNotFoundException, IOException, SQLException{
		POIDataFlowPackage poiDataFlowPackage = new POIDataFlowPackage("Test1");
		DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.xstreamDeserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataQueries/POI.xml");
		DbTaskQueryConfigItem queryConfigItem = dbDataFlowQueryConfigItem.getTaskDataQueryConfigList().get(0);
		DbQueryHelper dbQueryHelper = new DbQueryHelper(queryConfigItem, "CA96F0EBB6084AC8962DA21C084AA7BB");
		POITaskPackage poiTaskPackage = dbQueryHelper.query();
		poiDataFlowPackage.Add(poiTaskPackage);
		
		ArrayList<POIDataItemBase> dbDataList = poiTaskPackage.getDbDataList();
		ArrayList<POIDataItemBase> achieveDataList = new ArrayList<>();

		for(POIDataItemBase dataItem : dbDataList){
			if (dataItem.getTableName().equalsIgnoreCase("mdb_poi") || dataItem.getTableName().equalsIgnoreCase("mdb_poi_chargepile")){
				achieveDataList.add(dataItem);
			}
		}
		
		//assertEquals(0, achieveDataList.size());
	}
	
	@Test
	public void testCheckMarkDataSave() throws ClassNotFoundException, IOException {
		POIDataFlowPackage package1 = POIDataFlowPackage.deserialize("D:/JavaTools/MyTools/DataFlowSnaphsot12.1/DataFlowPackages/本部.dat");
		ArrayList<POIDataItemBase> dbDataList = package1.getTaskPackageList().get(10).getDbDataList();
		for (POIDataItemBase item : dbDataList) {
			String dbTableName = item.getTableName();
			if (dbTableName.equalsIgnoreCase("mdb_poi_chargepile")){
				//log.info(item.getDataMap().get("TAG_ATTR_ID").toString());
				log.info(item.getDataMap().get("CGUID"));
			}
		}
	}
	
	@Test
	public void testSpecial() throws ClassNotFoundException, IOException {
		POIDataFlowPackage package1 = POIDataFlowPackage.deserialize("E:/NetBeans_Projects/AutonaviDataFlowSnapshotUI/DataFlowPackages/专项测试包4.dat");
		ArrayList<POIDataItemBase> dbDataList = package1.getTaskPackageList().get(0).getDbDataList();
		ArrayList<POIDataItemBase> matArrayList = new ArrayList<>();
		for (POIDataItemBase item : dbDataList) {
			String dbTableName = item.getTableName();
			if (dbTableName.equalsIgnoreCase("ref_post_third_mat")){
				log.info(dbTableName + ":" + item.getDataMap().get("MATERIAL_ID").toString());
				matArrayList.add(item);
			}
		}
		//log.info("ref_post_third_mat: " + matArrayList.size());
		
		ArrayList<POIDataItemBase> simiArrayList = new ArrayList<>();
		for (POIDataItemBase item : dbDataList) {
			String dbTableName = item.getTableName();
			if (dbTableName.equalsIgnoreCase("ref_rel_post_third_simi")){
				log.info(dbTableName + ":" + item.getDataMap().get("MATERIAL_ID").toString());
				simiArrayList.add(item);
			}
		}
		log.info("ref_rel_post_third_simi: " + simiArrayList.size());
		
		ArrayList<POIDataItemBase> invlArrayList = new ArrayList<>();
		for (POIDataItemBase item : dbDataList) {
			String dbTableName = item.getTableName();
			if (dbTableName.equalsIgnoreCase("ref_rel_post_third_invl")){
				log.info(dbTableName + ":" + item.getDataMap().get("MATERIAL_ID").toString());
				invlArrayList.add(item);
			}
		}
		log.info("ref_rel_post_third_invl: " + invlArrayList.size());
	}

}
