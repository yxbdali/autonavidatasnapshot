/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;
import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author xiangbin.yang
 *
 */
@XStreamAlias("TaskQueryItem")
public class DbTaskQueryConfigItem extends DbDataQueryBase implements Serializable {
	public DbTaskQueryConfigItem(){
		super();
	}
	
	public DbTaskQueryConfigItem(String taskStage, String tableName, String clause, ArrayList<DbTableQueryConfigItem> dbTableQueryConfgList){
		super(tableName, clause);
		this.taskStage = taskStage;
		this.dbTableQueryConfgList = dbTableQueryConfgList;
	}
	
	private String taskStage;
	
	/**
	 * Get task stage
	 * 
	 * @return
	 */
	public String getTaskStage(){
		return taskStage;
	}
	
	/**
	 * Set task stage
	 * 
	 * @param taskStage
	 */
	public void setTaskStage(String taskStage) {
		this.taskStage = taskStage;
	}
	
	@XStreamImplicit(itemFieldName = "TableQueryItem")
	private ArrayList<DbTableQueryConfigItem> dbTableQueryConfgList;
	
	/**
	 * Get task query database table config item list
	 * 
	 * @return
	 */
	public ArrayList<DbTableQueryConfigItem> getDbTableQueryConfigList() {
		if (dbTableQueryConfgList == null){
			dbTableQueryConfgList = new ArrayList<>();
		}
		return dbTableQueryConfgList;
	}
	
	/**
	 * Set task query database table config item list
	 * 
	 * @param dbTableQueryConfigList
	 */
	public void setDbTableQueryConfigList(ArrayList<DbTableQueryConfigItem> dbTableQueryConfigList) {
		this.dbTableQueryConfgList = dbTableQueryConfigList;
	}
	
	private transient ObservableList<DbTableQueryConfigItem> dbTableQueryConfigItemList;
	
	/**
	 * Get Data base query config item, this is just used for UI persent
	 * 
	 * @return
	 */
	public ObservableList<DbTableQueryConfigItem> getDbTableQueryConfigItemList() {
		if (dbTableQueryConfigItemList == null){
			dbTableQueryConfigItemList = FXCollections.observableArrayList(getDbTableQueryConfigList());
		}
		return dbTableQueryConfigItemList;
	}
}
