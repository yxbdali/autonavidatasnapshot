/**
 * 
 */
package com.autonavi.data.test;

import java.util.HashMap;

/**
 * @author xiangbin.yang
 *
 */
public class TaskDataItem extends DbDataItemBase {
	/**
	 * Default constructor
	 * 
	 */
	public TaskDataItem() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with given data map
	 * 
	 * @param dataMap
	 */
	public TaskDataItem(HashMap<String, Object> dataMap){
		super(dataMap);
	}
	
	private String taskName;
	
	/**
	 * Get task name
	 * 
	 * @return
	 */
	public String getTaskName(){
		if (taskName == null || taskName.equalsIgnoreCase("")){
			taskName = (String)getDataHashMap().get("TASK_NAME");
		}
		return taskName;
	}
	
	/**
	 * Set task name
	 * 
	 * @param taskName
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	
	private String taskId;
	
	/**
	 * Get task id;
	 * 
	 * @return
	 */
	public String getTaskId(){
		if (taskId == null || taskId.equalsIgnoreCase("")){
			taskId = (String)getDataHashMap().get("TASK_ID");
		}
		return taskId;
	}
	
	/**
	 * Set task id
	 * 
	 * @param taskId
	 */
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	
	/**
	 * Constructor with given task id
	 * 
	 * @param taskId
	 */
	public TaskDataItem(String taskId){
		String sql = String.format("SELECT * FROM TDB_INNER_TASK WHERE TASK_ID = '%s'", taskId);
		getDbDataItem(sql);
	}
}
