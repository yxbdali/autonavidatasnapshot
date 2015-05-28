/**
 * 
 */
package com.autonavi.data.test;

/**
 * @author xiangbin.yang
 *
 */
public class SqlBundle<T> {
	/**
	 * Constructor
	 * 
	 * @param sqlUpdate
	 * @param sqlInsert
	 * @param sqlSelect
	 */
	public SqlBundle(String sqlUpdate, String sqlInsert, String sqlSelect, T obj) {
		// TODO Auto-generated constructor stub
		this.sqlUpdate = sqlUpdate;
		this.sqlInsert = sqlInsert;
		this.sqlSelect = sqlSelect;
		this.obj = obj;
	}
	
	private String sqlUpdate;
	
	/**
	 * Get update sql
	 * 
	 * @return
	 */
	public String getSqlUpdate() {
		return sqlUpdate;
	}
	
	/**
	 * Set update sql
	 * 
	 * @param sqlUpdate
	 */
	public void setSqlUpdate(String sqlUpdate){
		this.sqlUpdate = sqlUpdate;
	}
	
	private String sqlInsert;
	
	/**
	 * Get insert sql
	 * 
	 * @return
	 */
	public String getSqlInsert() {
		return sqlInsert;
	}
	
	/**
	 * Set insert sql
	 * 
	 * @param sqlInsert
	 */
	public void setSqlInsert(String sqlInsert){
		this.sqlInsert = sqlInsert;
	}
	
	private String sqlSelect;
	
	/**
	 * Get select sql
	 * 
	 * @return
	 */
	public String getSqlSelect() {
		return sqlSelect;
	}
	
	/**
	 * Set select sql
	 * 
	 * @param sqlSelect
	 */
	public void setSqlSelect(String sqlSelect){
		this.sqlSelect = sqlSelect;
	}
	
	private T obj;
	
	public T getObj() {
		return obj;
	}
	
	public void setObj(T obj) {
		this.obj = obj;
	}
}
