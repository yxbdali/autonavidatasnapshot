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

import com.autonavi.test.yxb.lib.XStreamSerializeUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author xiangbin.yang
 *
 */
@XStreamAlias("DataBaseConfig")
public class DbConfigItem {
	/**
	 * Constructor
	 * 
	 * @param configName
	 * @param server
	 * @param port
	 * @param sid
	 * @param user
	 * @param password
	 */
	public DbConfigItem(String configName, String server, String port, String sid, String user, String password) {
		// TODO Auto-generated constructor stub
		this.configName = configName;
		this.server = server;
		this.port = port;
		this.sid = sid;
		this.user = user;
		this.password = password;
	}
	
	private String configName;
	
	/**
	 * Get data base config name
	 * 
	 * @return
	 */
	public String getConfigName() {
		return configName;
	}
	
	/**
	 * Set data base config name
	 * 
	 * @param configName
	 */
	public void setConfigName(String configName){
		this.configName = configName;
	}
	
	private String server;
	
	/**
	 * Get server
	 * 
	 * @return
	 */
	public String getServer() {
		return server;
	}
	
	/**
	 * Set server
	 * 
	 * @param server
	 */
	public void setServer(String server){
		this.server = server;
	}
	
	private String port;
	
	/**
	 * Get server port
	 * 
	 * @return
	 */
	public String getPort(){
		return port;
	}
	
	/**
	 * Set server port
	 * 
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	private String sid;
	
	/**
	 * Get sid
	 * 
	 * @return
	 */
	public String getSid() {
		return sid;
	}
	
	/**
	 * Set sid;
	 * 
	 * @param sid
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	private String user;
	
	/**
	 * Get user
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Set user
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	private String password;
	
	/**
	 * Get password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Serialize data base config item to xml file using XStream
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serialize(String file) throws FileNotFoundException, IOException {
		XStreamSerializeUtils.serialize(this, file);
	}
	
	/**
	 * Deserialize data base config item from xml file using XStream
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static DbConfigItem deserialize(String file) throws IOException {
		DbConfigItem dbConfigItem = null;
		XStream xStream = new XStream();
		xStream.processAnnotations(DbConfigItem.class);
		try (InputStream in = new FileInputStream(file); 
				InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8").newDecoder())) {
			dbConfigItem = (DbConfigItem)xStream.fromXML(reader);
		}
		return dbConfigItem;
	}
}
