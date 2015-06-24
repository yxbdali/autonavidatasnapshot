/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author xiangbin.yang
 *
 */
public class DbDataQueryBase implements Serializable {
	/**
	 * Default Constructor
	 * 
	 */
	public DbDataQueryBase(){		
	}
	
	/**
	 * Constructor with table name and sql query clause
	 * 
	 * @param tableName
	 * @param clause
	 */
	public DbDataQueryBase(String tableName, String clause) {
		// TODO Auto-generated constructor stub
		this.tableName = tableName;
		this.clause = clause;
	}
	
	private String tableName;
	
	/**
	 * Get db table name
	 * 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * Set db table name
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
		getTableNameProperty().set(tableName);
		
	}
	
	private SimpleStringProperty tableNameProperty;
	
	/**
	 * Get table name string property
	 * 
	 * @return
	 */
	public SimpleStringProperty getTableNameProperty() {
		if (tableNameProperty == null){
			tableNameProperty = new SimpleStringProperty(tableName);
		}
		return tableNameProperty;
	}
	
	private String clause;
	
	/**
	 * the clause is a sql clause it will have a [TASKID] place hold 
	 * which will be replaced when running
	 * 
	 * @return
	 */
	public String getClause() {
		return clause;
	}
	
	/**
	 * Set sql clause
	 * 
	 * @param clause
	 */
	public void setClause(String clause){
		this.clause = clause;
	}
	
	private SimpleStringProperty clauseProperty;
	
	/**
	 * Get clause property
	 * 
	 * @return
	 */
	public SimpleStringProperty getClauseProperty() {
		if (clauseProperty == null){
			clauseProperty = new SimpleStringProperty(clause);
		}
		return clauseProperty;
	}
	
	@XStreamImplicit(itemFieldName = "clauseItem")
	private ArrayList<String> rollbackQueryClauseFieldList;
	
	/**
	 * Get query clause fields when want to rollback records
	 * 
	 * @return
	 */
	public ArrayList<String> getRollbackQueryClauseFieldList() {
		if (rollbackQueryClauseFieldList == null){
			rollbackQueryClauseFieldList = new ArrayList<>();
		}
		return rollbackQueryClauseFieldList;
	}
	
	/**
	 * Set query clause fields when want to rollback records
	 * 
	 * @param rollbackQueryClauseFieldList
	 */
	public void setRollbackQueryClauseFieldList(ArrayList<String> rollbackQueryClauseFieldList) {
		this.rollbackQueryClauseFieldList = rollbackQueryClauseFieldList;
	}
	
	@XStreamImplicit(itemFieldName = "insertExcludeItem")
	private ArrayList<String> insertExcludeColumnList;
	
	/**
	 * Get columns that when insert a new record we don't need to privide values for them
	 * 
	 * @return
	 */
	public ArrayList<String> getInsertExcludeColumnList() {
		if (insertExcludeColumnList == null){
			insertExcludeColumnList = new ArrayList<>();
		}
		return insertExcludeColumnList;
	}
	
	/**
	 * Set insert exclude column list
	 * 
	 * @param insertExcludeColumnList
	 */
	public void setInsertExcludeColumnList(ArrayList<String> insertExcludeColumnList) {
		this.insertExcludeColumnList = insertExcludeColumnList;
	}
	
	@XStreamImplicit(itemFieldName = "viewColumnItem")
	private ArrayList<String> viewColumnList;
	
	/**
	 * Set view columns that need to be show to users
	 * 
	 * @param viewColumnList
	 */
	public void setViewColumnList(ArrayList<String> viewColumnList) {
		this.viewColumnList = viewColumnList;
	}
	
	/**
	 * Get view columns
	 * 
	 * @return
	 */
	public ArrayList<String> getViewColumnList() {
		if (viewColumnList == null){
			viewColumnList = new ArrayList<>();
		}
		return viewColumnList;
	}
}
