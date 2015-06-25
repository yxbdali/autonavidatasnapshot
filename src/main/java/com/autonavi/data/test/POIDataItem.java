/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author xiangbin.yang
 *
 */
public class POIDataItem extends POIDataItemBase implements Serializable {
	public POIDataItem(String poiId, String poiChnName, Map<String, Object> dataMap) {
		// TODO Auto-generated constructor stub
		setDataMap(dataMap);
		this.poiChnName = poiChnName;
		this.poiId = poiId;
		setPOIId(poiId);
	}
	
	private String poiId;

	/**
	 * Get POI Id
	 * 
	 * @return
	 */
	public String getPOIId() {
		return getPOIIdProperty().get();
	}

	/**
	 * Set POI Id
	 * 
	 * @param poiId
	 */
	public void setPOIId(String poiId) {
		getPOIIdProperty().set(poiId);
		this.poiId = poiId;
	}
	
	private transient StringProperty poiIdProperty;
	
	public StringProperty getPOIIdProperty() {
		if (poiIdProperty == null){
			poiIdProperty = new SimpleStringProperty(poiId);
		}
		return poiIdProperty;
	}

	private String poiChnName;

	/**
	 * Get POI chinese name
	 * 
	 * @return
	 */
	public String getPOIChnName() {
		return poiChnName;
	}

	/**
	 * Set POI chinese name
	 * 
	 * @param poiChnName
	 */
	public void setPOIChnName(String poiChnName) {
		this.poiChnName = poiChnName;
	}
}
