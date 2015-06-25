/**
 * 
 */
package com.autonavi.data.test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.autonavi.test.yxb.lib.BinarySerializeUtils;

/**
 * @author xiangbin.yang
 *
 */
public class DbDataItemBase implements Serializable {
	/**
	 * Default Constructor
	 * 
	 */
	public DbDataItemBase() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param dataHashMap
	 */
	public DbDataItemBase(Map<String, Object> dataHashMap) {
		// TODO Auto-generated constructor stub
		this.dataHashMap = dataHashMap;
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
	
	private Map<String, Object> dataHashMap;
	
	/**
	 * Get dataHashMap
	 * 
	 * @return
	 */
	public Map<String, Object> getDataHashMap() {
		return dataHashMap;
	}
	
	/**
	 * Set dataHashMap
	 * 
	 * @param dataHashMap
	 */
	public void setDataHashMap(Map<String, Object> dataHashMap) {
		this.dataHashMap = dataHashMap;
	}
	
	public void getDbDataItem(String sql) {
		Map<String, Object> dataHashMap = null;
		DbDataItemBase dbDataItemBase = null;
		
		try(DBDriver myDbDriver = new DBDriver()) {	
			dataHashMap = myDbDriver.getFirstDataSet(sql);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.dataHashMap = dataHashMap;
	}
	
	public void save(String rootDir) throws IOException {
		
		saveDate = new Date();
		String saveTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(saveDate);
		
		File fRootDir = new File(rootDir);
		if (!fRootDir.exists()){
			fRootDir.mkdir();
		}
		
		String saveDir = rootDir + File.separator + saveTime;
		File fSaveDir = new File(saveDir);
		fSaveDir.mkdir();
		
		String saveFileName = saveDir + File.separator + dataHashMap.get("GUID") + ".dat";
		//XmlSerializeUtils.SaveToXml(this, saveFileName);
		BinarySerializeUtils.save(this, saveFileName);
		
	}
	
	/**
	 * Deserialze Db data item base
	 * 
	 * @param filePath
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static DbDataItemBase deserialize(String filePath) throws ClassNotFoundException, IOException {
		Object object = BinarySerializeUtils.deserialize(filePath);
		return (DbDataItemBase)object;
	}
}
