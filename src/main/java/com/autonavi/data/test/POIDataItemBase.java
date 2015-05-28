/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author xiangbin.yang
 *
 */
public class POIDataItemBase implements Serializable {

	private String tableName;

	/**
	 * Get table name
	 * 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Set table name
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	private HashMap<String, Object> dataMap;

	/**
	 * Get data map
	 * 
	 * @return
	 */
	public HashMap<String, Object> getDataMap() {
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
		}
		return dataMap;
	}

	/**
	 * Set data map
	 * 
	 * @param dataMap
	 */
	public void setDataMap(HashMap<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
}
