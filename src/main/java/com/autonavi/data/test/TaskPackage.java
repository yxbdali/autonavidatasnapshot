/**
 * 
 */
package com.autonavi.data.test;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


import com.autonavi.test.yxb.lib.BinarySerializeUtils;

/**
 * @author xiangbin.yang
 *
 */
public class TaskPackage implements Serializable {
	public TaskPackage(String taskId) throws SQLException {
		// TODO Auto-generated constructor stub
		this.taskId = taskId;
		
		getTaskPackage(taskId);
		
		this.taskName = taskItem.getTaskName();
	}
	

	/**
	 * Constructor
	 * 
	 * @param taskId
	 * @param taskName
	 * @param taskType
	 * @throws SQLException
	 */
	public TaskPackage(String taskId, String taskName, String taskType) throws SQLException {
		// TODO Auto-generated constructor stub
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskType = taskType;

		getTaskPackage(taskId);
	}

	private TaskDataItem taskItem;

	/**
	 * Get task item
	 * 
	 * @return
	 */
	public TaskDataItem getTaskItem() {
		return taskItem;
	}

	/**
	 * Set task item
	 * 
	 * @param taskItem
	 */
	public void setTaskItem(TaskDataItem taskItem) {
		this.taskItem = taskItem;
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
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Set task name
	 * 
	 * @param taskName
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	private String taskType;

	/**
	 * get task type
	 * 
	 * @return
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * Set task type
	 * 
	 * @param taskType
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	private ArrayList<PDAPOIDataItem> pdaPOIDataItemList;

	/**
	 * Get PDA POI data item list
	 * 
	 * @return
	 */
	public ArrayList<PDAPOIDataItem> getPDAPOIDataItemList() {
		if (pdaPOIDataItemList == null) {
			pdaPOIDataItemList = new ArrayList<>();
		}
		return pdaPOIDataItemList;
	}

	/**
	 * Set PDA POI data item list
	 * 
	 * @param pdaPOIDataItemList
	 */
	public void setPDAPOIDataItemList(ArrayList<PDAPOIDataItem> pdaPOIDataItemList) {
		this.pdaPOIDataItemList = pdaPOIDataItemList;
	}

	private ArrayList<DataFlowItem> dataFlowItemList;

	/**
	 * Get data flow item list
	 * 
	 * @return
	 */
	public ArrayList<DataFlowItem> getDataFlowItemList() {
		if (dataFlowItemList == null) {
			dataFlowItemList = new ArrayList<>();
		}
		return dataFlowItemList;
	}

	/**
	 * Set data flow item list
	 * 
	 * @param dataFlowItemList
	 */
	public void setDataFlowItemList(ArrayList<DataFlowItem> dataFlowItemList) {
		this.dataFlowItemList = dataFlowItemList;
	}

	private InnerTaskDataItem innertTask;

	/**
	 * Get innert task
	 * 
	 * @return
	 */
	public InnerTaskDataItem getInnerTask() {
		return innertTask;
	}

	/**
	 * Set innert task
	 * 
	 * @param innerTask
	 */
	public void setInnertTask(InnerTaskDataItem innerTask) {
		this.innertTask = innerTask;
	}

	/**
	 * Get a task package that contains all poi datas
	 * 
	 * @param taskId
	 * @param taskType
	 * @param taskName
	 * @return
	 * @throws SQLException
	 */
	private void getTaskPackage(String taskId) throws SQLException {
		// TaskPackage taskPkg = new TaskPackage(taskId, taskName, taskType);
		taskItem = new TaskDataItem(taskId);
		String sql = String.format("SELECT * FROM mdb_poi_edit WHERE mdb_poi_edit.guid "
				+ "in (SELECT feature_id FROM tdb_data_flow WHERE task_id = '%s')", taskId);
		
		String sqlQueryDataFlow = String.format("SELECT * FROM tdb_data_flow WHERE feature_id "
				+ "in (SELECT feature_id FROM tdb_data_flow WHERE task_id = '%s')", taskId);

		innertTask = new InnerTaskDataItem();
		innertTask.getInnerTaskDataItem(taskId);

		try (DBDriver dbDriver = new DBDriver()) {
			ArrayList<PDAPOIDataItem> pdapoiDataItemList = new ArrayList<>();
			ArrayList<DataFlowItem> dataFlowItemList = new ArrayList<>();
			ArrayList<HashMap<String, Object>> poiDatas = dbDriver.getDataSetList(sql);
			ArrayList<HashMap<String, Object>> dataFlows = dbDriver.getDataSetList(sqlQueryDataFlow);
			for (HashMap<String, Object> poiData : poiDatas) {
				PDAPOIDataItem pdapoiDataItem = new PDAPOIDataItem(poiData);
				pdapoiDataItemList.add(pdapoiDataItem);
			}
			for(HashMap<String, Object> dataFlow : dataFlows) {
				DataFlowItem dataFlowItem = new DataFlowItem(dataFlow);
				dataFlowItemList.add(dataFlowItem);
			}
			this.setPDAPOIDataItemList(pdapoiDataItemList);
			this.setDataFlowItemList(dataFlowItemList);
		}
	}

	/**
	 * Serialize task package into binary file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void save(String file) throws IOException {
		BinarySerializeUtils.save(this, file);
	}

	/**
	 * Deserialize task package from given binary file
	 * 
	 * @param file
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static TaskPackage deserialize(String file) throws ClassNotFoundException, IOException {
		TaskPackage taskPackage = (TaskPackage) BinarySerializeUtils.deserialize(file);
		HashMap<String, Object> dataMap = taskPackage.taskItem.getDataHashMap();
		taskPackage.setTaskId((String) dataMap.get("TASK_ID"));
		taskPackage.setTaskName((String) dataMap.get("TASK_NAME"));
		taskPackage.setTaskType((String) dataMap.get("TASK_TYPE"));

		return taskPackage;
	}
}
