/**
 * 
 */
package com.autonavi.data.test;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author xiangbin.yang
 *
 */
public class PDAPOIDataItem extends DbDataItemBase {
	/**
	 * Default constructor
	 * 
	 */
	public PDAPOIDataItem() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param dataMap
	 */
	public PDAPOIDataItem(HashMap<String, Object> dataMap) {
		// TODO Auto-generated constructor stub
		super(dataMap);
	}
	
	public void getPDAPOIDataItem(String guid) {
		String sql = String.format("SELECT * FROM mdb_poi_edit WHERE mdb_poi_edit.guid='%s'", guid);
		super.getDbDataItem(sql);
	}
}
