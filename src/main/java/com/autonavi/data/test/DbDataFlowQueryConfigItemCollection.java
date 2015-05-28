/**
 * 
 */
package com.autonavi.data.test;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author xiangbin.yang
 *
 */
public class DbDataFlowQueryConfigItemCollection {
	private String dataFlowType;
	
	/**
	 * Get data flow type
	 * 
	 * @return
	 */
	public String getDataFlowType() {
		return dataFlowType;
	}
	
	/**
	 * Set data flow type
	 * 
	 * @param dataFlowType
	 */
	public void setDataFlowType(String dataFlowType) {
		this.dataFlowType = dataFlowType;
		getDataFlowTypeProperty().set(dataFlowType);
	}
	
	private SimpleStringProperty dataFlowTypeProperty;
	
	/**
	 * Get data flow type property
	 * 
	 * @return
	 */
	public SimpleStringProperty getDataFlowTypeProperty() {
		if (dataFlowTypeProperty == null){
			dataFlowTypeProperty = new SimpleStringProperty();
		}
		return dataFlowTypeProperty;
	}
	
	private ArrayList<DbDataFlowQueryConfigItem> dbDataFlowQueryConfigItemList;
	
	/**
	 * Get data flow query config item list
	 * 
	 * @return
	 */
	public ArrayList<DbDataFlowQueryConfigItem> getDbDataFlowQueryConfigItemList() {
		if (dbDataFlowQueryConfigItemList == null){
			dbDataFlowQueryConfigItemList = new ArrayList<>();
		}
		return dbDataFlowQueryConfigItemList;
	}
}
