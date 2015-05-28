/**
 * 
 */
package com.autonavi.data.test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.autonavi.test.yxb.lib.BinarySerializeUtils;

/**
 * @author xiangbin.yang
 *
 */
public class PDADataSnapshotList extends ArrayList<PDADataSnapshot> {
	private String snapshotFile;
	
	/**
	 * Set PDA data snapshot file value
	 * 
	 * @param snapshotFile
	 */
	public void setSnapshotFile(String snapshotFile) {
		this.snapshotFile = snapshotFile;
	}

	/**
	 * Save PDA Data snapshot list to binary file
	 * 
	 * @param rootDir
	 * @throws IOException
	 */
	public void save(String rootDir) throws IOException {
		if (snapshotFile == null) {
			Date saveDate = new Date();
			String saveTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(saveDate);

			File fRootDir = new File(rootDir);
			if (!fRootDir.exists()) {
				fRootDir.mkdir();
			}

			String saveDir = rootDir + File.separator + saveTime;
			File fSaveDir = new File(saveDir);
			fSaveDir.mkdir();

			String guid = this.get(0).getGuid();
			String saveFileName = saveDir + File.separator + guid + ".dat";
			saveSnapshot(saveFileName);
		}
		else {
			saveSnapshot(snapshotFile);
		}
	}

	/**
	 * Save PDA data snapshot list to binary file
	 * 
	 * @param dataSnapshotFile
	 * @throws IOException
	 */
	public void saveSnapshot(String dataSnapshotFile) throws IOException {
		BinarySerializeUtils.save(this, dataSnapshotFile);
	}
	
	/**
	 * Deserialze PDA data snapshot from given binary file
	 * 
	 * @param dataSnapshotFile
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static PDADataSnapshotList deserialze(String dataSnapshotFile) throws ClassNotFoundException, IOException {
		PDADataSnapshotList object = (PDADataSnapshotList)BinarySerializeUtils.deserialize(dataSnapshotFile);
		object.setSnapshotFile(dataSnapshotFile);
		return object;
	}
}
