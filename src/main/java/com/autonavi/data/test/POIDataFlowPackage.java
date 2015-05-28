/**
 * 
 */
package com.autonavi.data.test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.autonavi.test.yxb.lib.BinarySerializeUtils;

/**
 * @author xiangbin.yang
 *
 */
public class POIDataFlowPackage implements Serializable {
	
	/**
	 * Constructor
	 * 
	 * @param packageName
	 */
	public POIDataFlowPackage(String packageName) {
		// TODO Auto-generated constructor stub
		this.packageName = packageName;
		createDate = new Date();
	}
	
	private String packageName;
	
	/**zA
	 * Get package name
	 * 
	 * @return
	 */
	public String getPackageName() {
		return getPackageNameProperty().get();
	}
	
	/**
	 * Set package name
	 * 
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		getPackageNameProperty().set(packageName);
		this.packageName = packageName;
	}
	
	private transient StringProperty packageNameProperty;
	
	public StringProperty getPackageNameProperty() {
		if (packageNameProperty == null){
			packageNameProperty = new SimpleStringProperty(packageName);
		}
		return packageNameProperty;
	}
	
	private Date createDate;
	
	/**
	 * Get create poi data flow package date time
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return getCreateDataProperty().get();
	}
	
	/**
	 * Set create poi data flow package date time
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		getCreateDataProperty().set(createDate);
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
	
	private transient ObjectProperty<Date> createDateProperty;
	
	public ObjectProperty<Date> getCreateDataProperty() {
		if (createDateProperty == null){
			createDateProperty = new SimpleObjectProperty<Date>(createDate);
		}
		return createDateProperty;
	}
	
	private ArrayList<POITaskPackage> taskPackageList;
	
	/**
	 * Get task package list
	 * 
	 * @return
	 */
	public ArrayList<POITaskPackage> getTaskPackageList() {
		if (taskPackageList == null){
			taskPackageList = new ArrayList<>();
		}
		return taskPackageList;
	}
	
	/**
	 * Set task package list
	 * 
	 * @param taskPackageList
	 */
	public void setTaskPackageList(ArrayList<POITaskPackage> taskPackageList){
		this.taskPackageListProperty = FXCollections.observableArrayList(taskPackageList);
		this.taskPackageList = taskPackageList;
	}
	
	private transient ObservableList<POITaskPackage> taskPackageListProperty;
	
	public ObservableList<POITaskPackage> getTaskPackageListProperty() {
		if (taskPackageListProperty == null){
			taskPackageListProperty = FXCollections.observableArrayList(getTaskPackageList());
		}
		return taskPackageListProperty;
	}
	
	/**
	 * Add a poi task item
	 * 
	 * @param poiTaskPackage
	 */
	public void Add(POITaskPackage poiTaskPackage){
		getTaskPackageList().add(poiTaskPackage);
		getTaskPackageListProperty().add(poiTaskPackage);
	}
	
	/**
	 * Delete a poi task pack from data flow pack
	 * 
	 * @param poiTaskPackage
	 */
	public void Delete(POITaskPackage poiTaskPackage){
		getTaskPackageList().remove(poiTaskPackage);
		getTaskPackageListProperty().remove(poiTaskPackage);
	}
	
	/**
	 * Serialize POI data flow package to binary file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void serialize(String file) throws IOException{
		BinarySerializeUtils.save(this, file);
	}
	
	/**
	 * Deserialze POI data flow package from binary file
	 * 
	 * @param file
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static POIDataFlowPackage deserialize(String file) throws ClassNotFoundException, IOException{
		return (POIDataFlowPackage)BinarySerializeUtils.deserialize(file);
	}
}
