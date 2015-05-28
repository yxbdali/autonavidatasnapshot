/**
 * 
 */
package com.autonavi.data.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.autonavi.test.yxb.lib.XStreamSerializeUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * @author xiangbin.yang
 *
 */
@XStreamAlias("DbConfigList")
public class DbConfigList {
	@XStreamImplicit(itemFieldName = "dbConfigItem")
	private ArrayList<DbConfigItem> dbConfigItemList;
	
	/**
	 * Get db config item list
	 * 
	 * @return
	 */
	public ArrayList<DbConfigItem> getDbConfigItemList() {
		if (dbConfigItemList == null){
			dbConfigItemList = new ArrayList<>();
		}
		return dbConfigItemList;
	}
	
	/**
	 * Set db config item list
	 * 
	 * @param dbConfigItemList
	 */
	public void setDbConfigItemList(ArrayList<DbConfigItem> dbConfigItemList) {
		this.dbConfigItemList = dbConfigItemList;
	}
	
	/**
	 * Add dbConfigItem
	 * 
	 * @param dbConfigItem
	 */
	public void add(DbConfigItem dbConfigItem) {
		getDbConfigItemList().add(dbConfigItem);
	}
	
	/**
	 * Serialize db config list to xml file using XStream
	 * 
	 * @param file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void serialize(String file) throws FileNotFoundException, IOException {
		XStreamSerializeUtils.serialize(this, file);
	}
	
	/**
	 * Deserialize db config list from xml file using XStream
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static DbConfigList deserialize(String file) throws FileNotFoundException, IOException {
		DbConfigList dbConfigList = null;
		XStream xStream = new XStream();
		xStream.processAnnotations(DbConfigList.class);
		try (InputStream in = new FileInputStream(file); 
				InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8").newDecoder())) {
			dbConfigList = (DbConfigList)xStream.fromXML(reader);
		}
		return dbConfigList;
	}
}
