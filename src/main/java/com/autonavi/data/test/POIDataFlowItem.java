/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;

/**
 * This class is used to store the tdb_data_flow data
 * 
 * @author xiangbin.yang
 *
 */
public class POIDataFlowItem extends POIDataItemBase implements Serializable{
	/**
	 * This is used to save tdb_data_flow data as a backup
	 * 
	 */
	private POIDataFlowItem poiDataFlowItem2;
	
	/**
	 * Set poiDataFlowItem2
	 * 
	 * @return
	 */
	public POIDataFlowItem getPOIDataFlowItem2() {
		return poiDataFlowItem2;
	}
	
	/**
	 * Get poiDataFlowItem2
	 * 
	 * @param poiDataFlowItem2
	 */
	public void setPOIDataFlowItem2(POIDataFlowItem poiDataFlowItem2) {
		this.poiDataFlowItem2 = poiDataFlowItem2;
	}
}
