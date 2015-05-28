/**
 * 
 */
package com.autonavi.data.test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.autonavi.test.yxb.lib.*;

/**
 * @author xiangbin.yang
 *
 */
public class DataFlowPackage implements Serializable {
	
	/**
	 * Default constructor
	 * 
	 */
	public DataFlowPackage() {
		// TODO Auto-generated constructor stub
		createDate = new Date();
	}
	
	public DataFlowPackage(String packageName){
		this();
		this.packageName = packageName;
	}
	
	private String packageName;
	
	/**
	 * Get package name
	 * 
	 * @return
	 */
	public String getPackageName() {
		return packageName;
	}
	
	/**
	 * Set package name
	 * 
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	private Date createDate;
	
	/**
	 * Get data flow snapshot package create date
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * Set date flow snapshot package create date
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	private String dataFlowPackageType;
	
	/**
	 * Get data flow package type
	 * 
	 * @return
	 */
	public String getDataFlowPackageType(){
		return dataFlowPackageType;
	}
	
	/**
	 * Set data flow package type
	 * 
	 * @param dataFlowPackageType
	 */
	public void setDataFlowPackageType(String dataFlowPackageType){
		this.dataFlowPackageType = dataFlowPackageType;
	}
	
	private ArrayList<TaskPackage> innerTaskPackageList;
	
	/**
	 * Get task package list
	 * 
	 * @return
	 */
	public ArrayList<TaskPackage> getInnerTaskPackageList() {
		if (innerTaskPackageList == null){
			innerTaskPackageList = new ArrayList<>();
		}
		return innerTaskPackageList;
	}
	
	/**
	 * Set task package list
	 * 
	 * @param taskPackageList
	 */
	public void setTaskPackageList(ArrayList<TaskPackage> taskPackageList) {
		this.innerTaskPackageList = taskPackageList;
	}
	
	public void add(TaskPackage taskPackageItem) {
		getInnerTaskPackageList().add(taskPackageItem);
	}
	
	/**
	 * Serialize object to a file as binary format
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void serialize(String file) throws IOException {
		BinarySerializeUtils.save(this, file);
	}
	
	/**
	 * Deserialize object from a binary file
	 * 
	 * @param file
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static DataFlowPackage deserialize(String file) throws ClassNotFoundException, IOException {
		return (DataFlowPackage)BinarySerializeUtils.deserialize(file);
	}
}
