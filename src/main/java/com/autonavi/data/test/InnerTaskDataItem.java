/**
 * 
 */
package com.autonavi.data.test;

import java.security.PublicKey;
import java.util.HashMap;

/**
 * @author xiangbin.yang
 *
 */
public class InnerTaskDataItem extends DbDataItemBase {
	/**
	 * Default constructor
	 * 
	 */
	public InnerTaskDataItem() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with given data hash map
	 * 
	 * @param dataMap
	 */
	public InnerTaskDataItem(HashMap<String, Object> dataMap){
		super(dataMap);
	}
	
	private String taskId;
	
	/**
	 * Get task id
	 * 
	 * @return
	 */
	public String getTaskId(){
		if(taskId.equalsIgnoreCase("")){
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
	
	private String taskName;
	
	/**
	 * Get task name
	 * 
	 * @return
	 */
	public String getTaskName() {
		if (taskName.equalsIgnoreCase("")){
			taskName = (String)getDataHashMap().get("TASK_NAME");
		}
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
	
	/**
	 * Get innert task from given task id
	 * 
	 * @param taskId
	 */
	public void getInnerTaskDataItem(String taskId) {
		String sql = String.format("SELECT * FROM tdb_inner_task WHERE task_id='%s'", taskId);
		super.getDbDataItem(sql);
	}
}
