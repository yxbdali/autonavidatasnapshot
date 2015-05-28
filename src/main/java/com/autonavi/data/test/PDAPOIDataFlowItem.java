package com.autonavi.data.test;

import java.io.Serializable;
import java.util.HashMap;

public class PDAPOIDataFlowItem extends DbDataItemBase {
	public void getPDAPOIDataFlowItem(String featureId) {
		String sql = String.format("SELECT * FROM tdb_data_flow WHERE tdb_data_flow.feature_id='%s'", featureId);
		super.getDbDataItem(sql);
	}
}
