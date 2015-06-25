/**
 * 
 */
package com.autonavi.data.test;

import java.awt.List;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.DATE;
import oracle.sql.ORAData;

/**
 * @author xiangbin.yang
 *
 */
public class SqlBuilder {
	private static String getSqlAppendVal(Object valObject){
		String appendString = "";
		if (valObject instanceof String){
			String valString = valObject.toString();
			if (valString.contains("'")){
				valString.replaceAll("'", "''");
			}
			appendString = String.format("'%s'", valString);
		}
		else if (valObject instanceof oracle.sql.TIMESTAMP || valObject instanceof Timestamp) {
			String sValue = valObject.toString();
			if (sValue.endsWith(".0")){
				sValue = sValue.substring(0, sValue.length() - 2);
			}
			appendString = String.format("to_timestamp('%s', 'yyyy-mm-dd hh24:mi:ss:ff')", sValue);
		}
		else if (valObject instanceof oracle.sql.DATE || valObject instanceof java.sql.Date) {
			String sDateValue = valObject.toString();
			String[] sDateValueParts = sDateValue.split(".");
			sDateValue = sDateValueParts[0];
			
			appendString = String.format("to_date('%s', 'YYYY-MM-DD HH24:MI:SS')", sDateValue);
		}
		else if (valObject instanceof BigDecimal) {
			appendString = valObject.toString();
		}
		else if (valObject == null) {
			appendString = "null";
		}
		else if (valObject instanceof JGeometry){
			appendString = "?";
		}
		
		return appendString;
	}
	
	public static String buildInsertSql(String tableName, HashMap<String, Object> dataMap){
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO " + tableName + " ");
		
		Set<String> keySet = dataMap.keySet();
		Object[] keyArray = keySet.toArray();
		for(int i = 0; i < keyArray.length; i++){
			if (i == 0){
				sqlBuilder.append("(" + keyArray[i] + ", ");
			}
			else if (i == keyArray.length - 1) {
				sqlBuilder.append(keyArray[i] + ") ");
			}
			else {
				sqlBuilder.append(keyArray[i] + ", ");
			}
		}
		for(int i = 0; i < keyArray.length; i++){
			Object valObject = dataMap.get(keyArray[i]);
			String sValue = getSqlAppendVal(valObject);
			if (i == 0){
				sqlBuilder.append("VALUES (" + sValue + ", ");
			}
			else if (i == keyArray.length - 1) {
				sqlBuilder.append(sValue + ")");
			}
			else {
				sqlBuilder.append(sValue + ", ");
			}
		}
		
		return sqlBuilder.toString();
	}
	
	public static String buildUpdateSql(String tableName, HashMap<String, Object> dataMap, ArrayList<String> clauseList, HashMap<String, Object> clauseWordMap){
		StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName + " SET ");
		
		Set<String> keySet = dataMap.keySet();
		Object[] keyArray = keySet.toArray();
		for(int i = 0; i < keyArray.length; i++){
			String key = (String)keyArray[i];
			if (clauseList.contains(key)){
				continue;
			}
			
			Object value = dataMap.get(key);
			if (value instanceof String){
				String valueString = value.toString();
				if (valueString.contains("'")){
					valueString = valueString.replaceAll("'", "''");
				}
				sqlBuilder.append(key + "=" + "'" + valueString + "',");
			} 
			else if (value instanceof oracle.sql.TIMESTAMP) {
				String sValue = value.toString();
				if (sValue.endsWith(".0")){
					sValue = sValue.substring(0, sValue.length() - 2);
				}
				sqlBuilder.append(key + "=" + String.format("to_timestamp('%s', 'yyyy-mm-dd hh24:mi:ss:ff')", sValue) + ",");
			}
			else if (value instanceof java.sql.Date) {
				String sDateValue = value.toString();
				String[] sDateValueParts = sDateValue.split(".");
				sDateValue = sDateValueParts[0];
				
					sqlBuilder.append(key + "=" + String.format("to_date('%s', 'YYYY-MM-DD HH24:MI:SS')", sDateValue) + ",");
				
			}
			else if (value instanceof BigDecimal) {
				sqlBuilder.append(key + "=" + value.toString() + ",");
			}
			else if (value == null){
				sqlBuilder.append(key + "=null,");
			}
			
		}
		String sql = sqlBuilder.toString();
		if (sql.endsWith(",")){
			sql = sql.substring(0, sql.length() -1);
		}
		sqlBuilder = new StringBuilder(sql);
		sqlBuilder.append(" WHERE ");
		
		Set<String> clauseKeySet = clauseWordMap.keySet();
		for (String clauseKey : clauseKeySet) {
			Object clauseVal = clauseWordMap.get(clauseKey);
			if (clauseVal instanceof String){
				sqlBuilder.append(String.format("%s='%s' AND ", clauseKey, clauseVal));
			}
			else if (clauseVal instanceof BigDecimal || clauseVal instanceof Integer 
					|| clauseVal instanceof Double || clauseVal instanceof Float) {
				sqlBuilder.append(String.format("%s=%s AND ", clauseKey, clauseVal));
			}
		}
		
		sql = sqlBuilder.toString();
		if (sql.endsWith("AND ")){
			sql = sql.substring(0, sql.length() - 4);
		}
		
		return sql;
	}
	
	public static String buildUpdateSql(String tableName, HashMap<String, Object> dataMap, String clauseColumn, String clauseWord) {
		StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName + " SET ");
		
		Set<String> keySet = dataMap.keySet();
		Object[] keyArray = keySet.toArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = (String)keyArray[i];
			if (key.equalsIgnoreCase(clauseColumn) || key.equalsIgnoreCase("GEOM")){
				continue;
			}
			Object value = dataMap.get(key);
			if (value == null){
				continue;
			}
			if (value instanceof String){
				String valueString = value.toString();
				if (valueString.contains("'")){
					valueString = valueString.replaceAll("'", "''");
				}
				if (i < keyArray.length - 1){
					sqlBuilder.append(key + "=" + "'" + valueString + "',");
				}
				else {
					sqlBuilder.append(key + "=" + "'" + valueString + "' ");
				}
			}
			else if (value instanceof oracle.sql.TIMESTAMP || value instanceof Timestamp){
				//Date date = (Date)value;
				//String dateString = new SimpleDateFormat("")
				String sValue = value.toString();
				if (sValue.endsWith(".0")){
					sValue = sValue.substring(0, sValue.length() - 2);
				}
				if (i < keyArray.length - 1){
					sqlBuilder.append(key + "=" + String.format("to_timestamp('%s', 'yyyy-mm-dd hh24:mi:ss:ff')", sValue) + ",");
				}
				else {
					sqlBuilder.append(key + "=" + String.format("to_timestamp('%s', 'yyyy-mm-dd hh24:mi:ss:ff') ", sValue));
				}
			}
			else if (value instanceof oracle.sql.DATE || value instanceof java.sql.Date){
				String sDateValue = value.toString();
				String[] sDateValueParts = sDateValue.split(".");
				sDateValue = sDateValueParts[0];
				
				if (i < keyArray.length - 1){
					sqlBuilder.append(key + "=" + String.format("to_date('%s', 'YYYY-MM-DD HH24:MI:SS')", sDateValue) + ",");
				}
				else {
					sqlBuilder.append(key + "=" + String.format("to_date('%s', 'YYYY-MM-DD HH24:MI:SS') ", sDateValue));
				}
			}
			else if (value instanceof BigDecimal) {
				sqlBuilder.append(key + "=" + value.toString() + ",");
			}
	
		}
		String sql = sqlBuilder.toString();
		
		if (sql.endsWith(",")){
			sql = sql.substring(0, sql.length() -1);
		}
		
		sql += " WHERE " + clauseColumn + "='" + clauseWord + "'";
		return sql;
	}
	
	public static String buildSelectSql(String tableName, ArrayList<String> clauseList, HashMap<String, Object> clauseWordMap){
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
		
		String[] keyArray = (String[])clauseWordMap.keySet().toArray();
		for(int i = 0; i < keyArray.length; i++){
			String key = keyArray[i];
			Object valObject = clauseWordMap.get(key);
			String sValue = getSqlAppendVal(valObject);
			if (i < keyArray.length - 1){
				sqlBuilder.append(String.format("%s=%s AND ", key, sValue));
			}
			else {
				sqlBuilder.append(String.format("%s=%s", key, sValue));
			}
		}
		
		return sqlBuilder.toString();
	}
}
