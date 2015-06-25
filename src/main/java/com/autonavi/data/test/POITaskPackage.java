/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author xiangbin.yang
 * 
 * POITaskPackage contain two db data list, the one is poiDataList that used to 
 * store POI data, and the other one is dbDataList that used to save other tables data
 *
 */
public class POITaskPackage extends POIDataItemBase implements Serializable {
	/**
	 * Constructor
	 * 
	 * @param taskId
	 * @param taskName
	 * @param poiDataList
	 * @param dbDataList
	 */
	public POITaskPackage(String taskId, String taskName, String tableName, String dbConfigName, Map<String, Object> taskDataMap, List<POIDataItem> poiDataList, List<POIDataItemBase> dbDataList) {
		// TODO Auto-generated constructor stub
		super.setTableName(tableName);
		super.setDataMap(taskDataMap);
		this.poiDataList = poiDataList;
		this.dbDataList = dbDataList;
		this.taskName = taskName;
		this.taskId = taskId;
		this.dbConfigName = dbConfigName;
		//this.comments = new SimpleStringProperty(this.commentsVal);
	}
	
	/**
	 * This is used to save tdb_inner_task data as a backup
	 * 
	 */
	//private POITaskPackage poiTaskPackage2;
	
	/**
	 * Get poiTaskPackage2
	 * 
	 * @return
	 */
	/*
	public POITaskPackage getPOITaskPackage2() {
		return poiTaskPackage2;
	}
	*/
	
	/**
	 * Set poiTaskPackage2
	 * 
	 * @param poiTaskPackage2
	 */
	/*
	public void setPOITaskPackage2(POITaskPackage poiTaskPackage2) {
		this.poiTaskPackage2 = poiTaskPackage2;
	}
	*/

	private String taskType;
	
	/**
	 * Get task type
	 * 
	 * @return
	 */
	public String getTaskType() {
		if (taskType == null || taskType.equalsIgnoreCase("")){
			taskType = getTaskTypeName();
		}
		return taskType;
	}
	
	/**
	 * Set task type
	 * 
	 * @param taskType
	 */
	public void setTaskType(String taskType){
		this.taskType = taskType;
	}
	
	private String taskId;
	
	/**
	 * Get task id
	 * 
	 * @return
	 */
	public String getTaskId() {
		return taskId;
	}
	
	/**
	 * Set task id
	 * 
	 * @param taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	private String taskName;
	
	/**
	 * Get task name
	 * 
	 * @return
	 */
	public String getTaskName(){
		return taskName;
		//return getTaskNamePropertyObj().get();
	}
	
	/**
	 * Set task name
	 * 
	 * @param taskName
	 */
	public void setTaskName(String taskName){
		//getTaskNamePropertyObj().set(taskName);
		this.taskName = taskName;
	}
	
	private transient StringProperty taskNameProperty;
	
	public StringProperty getTaskNamePropertyObj() {
		if (taskNameProperty == null){
			taskNameProperty = new SimpleStringProperty(taskName);
		}
		return taskNameProperty;
	}
	
	private java.util.Date createDate;
	
	/**
	 * Get task package create date
	 * 
	 * @return
	 */
	public java.util.Date getCreateDate() {
		if (createDate == null) {
			createDate = new java.util.Date();
		}
		return createDate;
	}
	
	/**
	 * Set create date
	 * 
	 * @param createDate
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	private String sm;
	
	/**
	 * Get sm
	 * 
	 * @return
	 */
	public String getSm() {
		if(sm == null){
			sm = getSmName();
		}
		return sm;
	}
	
	/**
	 * Set sm
	 * 
	 * @param sm
	 */
	public void setSum(String sm){
		this.sm = sm;
	}
	
	private int taskDataCount = -1;
	
	/**
	 * Get task data count
	 * 
	 * @return
	 */
	public int getTaskDataCount() {
		if (taskDataCount == -1){
			taskDataCount = getTaskDataCountValue();
		}
		return taskDataCount;
	}
	
	/**
	 * Set task data count
	 * 
	 * @param taskDataCount
	 */
	public void setTaskDataCount(int taskDataCount) {
		this.taskDataCount = taskDataCount;
	}
	
	private String commentsVal;
	
	/**
	 * Get comments
	 * 
	 * @return
	 */
	public String getCommentsVal() {
		return commentsVal;
	}
	
	/**
	 * Set comments
	 * 
	 * @param comments
	 */
	public void setCommentsVal(String comments) {
		//getCommentsProperty().set(comments);
		this.commentsVal = comments;		
	}
	
	private transient SimpleStringProperty comments;
	
	public SimpleStringProperty getCommentsProperty() {
		if (comments == null){
			comments = new SimpleStringProperty(commentsVal);
		}
		return comments;
	}
	
	private String dataBaseId;
	
	/**
	 * Get data base id
	 * 
	 * @return
	 */
	
	public String getDataBaseId() {
		return dataBaseId;
	}
	
	
	/**
	 * Set data base id
	 * 
	 * @param dataBaseId
	 */
	
	public void setDataBaseId(String dataBaseId) {
		this.dataBaseId = dataBaseId;
	}
	
	
	private List<POIDataItem> poiDataList;
	
	/**
	 * Get POI data list
	 * 
	 * @return
	 */
	public List<POIDataItem> getPOIDataList() {
		if (poiDataList == null){
			poiDataList = new ArrayList<>();
		}
		return poiDataList;
	}
	
	/**
	 * Set POI data list
	 * 
	 * @param poiDataList
	 */
	public void setPOIDataList(ArrayList<POIDataItem> poiDataList) {
		this.poiDataList = poiDataList;
	}
	
	private transient ObservableList<POIDataItem> poiDataListProperty;
	
	/**
	 * Get ObservableList for POI data list
	 * 
	 * @return
	 */
	public ObservableList<POIDataItem> getPOIDataListProperty() {
		if (poiDataListProperty == null){
			poiDataListProperty = FXCollections.observableArrayList(poiDataList);
		}
		return poiDataListProperty;
	}
	
	private String dbConfigName;
	
	/**
	 * Get database config name
	 * 
	 * @return
	 */
	
	public String getDbConfigName() {
		return dbConfigName;
	}
	
	
	/**
	 * Set database config name
	 * 
	 * @param dbConfigName
	 */
	public void setDbConfigName(String dbConfigName) {
		this.dbConfigName = dbConfigName;
	}
	
	
	private List<POIDataItemBase> dbDataList;
	
	/**
	 * Get task's database table data package list
	 * 
	 * @return
	 */
	public List<POIDataItemBase> getDbDataList(){
		if (dbDataList == null){
			dbDataList = new ArrayList<>();
		}
		return dbDataList;
	}
	
	/**
	 * Set task's database table data package list
	 * 
	 * @param tableDataPackageList
	 */
	public void setDbDataList(ArrayList<POIDataItemBase> dbDataList){
		this.dbDataList = dbDataList;
	}
	
	private String getTaskTypeName(){
		String taskTypeName = "";
		BigDecimal _taskTypeInDb = (BigDecimal)getDataMap().get("TASK_TYPE");
		
		int _taskType = _taskTypeInDb.intValue();
		
		switch (_taskType) {
		case 1:
			taskTypeName = "内业生产";
			break;
		case 2:
			taskTypeName = "内检";
			break;
		case 3:
			taskTypeName = "返修";
			break;
		case 4:
			taskTypeName = "返工";
			break;
		case 5:
			taskTypeName = "质检";
			break;
		case 6:
			taskTypeName = "高德验收";
			break;
		case 7:
			taskTypeName = "核标任务";
			break;
		default:
			break;
		}
		
		return taskTypeName;
	}
	
	private String getSmName(){
		String sm = "";
		int _sm = -1;
		try {
			_sm = ((BigDecimal)getDataMap().get("SM")).intValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		switch (_sm) {
		case 0:
			sm = "非重点数据";
			break;
		case 1:
			sm = "重点数据";
			break;
		case 2:
			
			break;
		case 3:
			sm = "充电站";
			break;
		default:
			break;
		}
		
		return sm;
	}
	
	private int getTaskDataCountValue(){
		int value = -1;
		Object object = getDataMap().get("TASK_DATA_COUNT");
		if (object != null){
			value = ((BigDecimal)object).intValue();
		}
		
		return value;
	}
}
