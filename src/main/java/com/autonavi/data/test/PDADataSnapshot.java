/**
 * 
 */
package com.autonavi.data.test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;

import com.autonavi.test.yxb.lib.BinarySerializeUtils;

/**
 * @author xiangbin.yang
 *
 */
public class PDADataSnapshot implements Serializable {
	/**
	 * Default constructor
	 * 
	 */
	public PDADataSnapshot() {
		// TODO Auto-generated constructor stub
		pdapoiDataSnapshot = new PDAPOIDataItem();
		pdapoiDataFlowSnapshot = new PDAPOIDataFlowItem();
	}

	private transient SimpleStringProperty guid;

	/**
	 * Get data guid
	 * 
	 * @return
	 */
	public String getGuid() {
		if (guid == null) {
			Map<String, Object> dataHashMap = pdapoiDataSnapshot.getDataHashMap();
			if (dataHashMap != null) {
				guid = new SimpleStringProperty((String) pdapoiDataSnapshot.getDataHashMap().get("GUID"));
			}
		}
		return guid.get();
	}

	private Date saveDate;

	/**
	 * Get save date
	 * 
	 * @return
	 */
	public Date getSaveDate() {
		return saveDate;
	}

	/**
	 * Set save date
	 * 
	 * @param saveDate
	 */
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	private PDAPOIDataItem pdapoiDataSnapshot;

	/**
	 * Get PDA POI data snapshot item
	 * 
	 * @return
	 */
	public PDAPOIDataItem getPDAPOISnapshot() {
		return pdapoiDataSnapshot;
	}

	/**
	 * Set PDA POI data snapshot item
	 * 
	 * @param pdapoiDataSnapshot
	 */
	public void setPDAPOISnapshot(PDAPOIDataItem pdapoiDataSnapshot) {
		this.pdapoiDataSnapshot = pdapoiDataSnapshot;
	}

	private PDAPOIDataFlowItem pdapoiDataFlowSnapshot;

	/**
	 * Get PDA data flow snapshot
	 * 
	 * @return
	 */
	public PDAPOIDataFlowItem getPDAPOIDataFlowSnapshot() {
		return pdapoiDataFlowSnapshot;
	}

	/**
	 * Set PDA data flow snapshot
	 * 
	 * @param pdapoiDataFlowSnapshot
	 */
	public void setPDAPOIDataFlowSnapshot(PDAPOIDataFlowItem pdapoiDataFlowSnapshot) {
		this.pdapoiDataFlowSnapshot = pdapoiDataFlowSnapshot;
	}

	/**
	 * Get PDA data snapshot
	 * 
	 * @param guid
	 */
	public void getPDADataSnapshotData(String guid) {
		pdapoiDataSnapshot.getPDAPOIDataItem(guid);
		pdapoiDataFlowSnapshot.getPDAPOIDataFlowItem(guid);
	}

	/**
	 * Save PDA Data snapshot to binary file
	 * 
	 * @param rootDir
	 * @throws IOException
	 */
	public void save(String rootDir) throws IOException {
		saveDate = new Date();
		String saveTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(saveDate);

		File fRootDir = new File(rootDir);
		if (!fRootDir.exists()) {
			fRootDir.mkdir();
		}

		String saveDir = rootDir + File.separator + saveTime;
		File fSaveDir = new File(saveDir);
		fSaveDir.mkdir();

		String saveFileName = saveDir + File.separator + getGuid() + ".dat";
		// XmlSerializeUtils.SaveToXml(this, saveFileName);
		BinarySerializeUtils.save(this, saveFileName);
	}

	/**
	 * Deserialze PDA data snapshot from binary file
	 * 
	 * @param filePath
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static PDADataSnapshot deserialize(String filePath) throws ClassNotFoundException, IOException {
		Object object = BinarySerializeUtils.deserialize(filePath);
		return (PDADataSnapshot) object;
	}
}
