/**
 * 
 */
package com.autonavi.data.test;

import java.util.HashMap;

/**
 * @author xiangbin.yang
 *
 */
public class DataFlowItem extends DbDataItemBase {
	public DataFlowItem() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with data map
	 * 
	 * @param dataMap
	 */
	public DataFlowItem(HashMap<String, Object> dataMap){
		super(dataMap);
	}
	
	public void getDataFlowItem(String featureId) {
		String sql = String.format("SELECT * FROM tdb_data_flow WHERE feature_id='%s'", featureId);
		super.getDbDataItem(sql);
	}
}
