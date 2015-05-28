/**
 * 
 */
package com.autonavi.data.test;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author xiangbin.yang
 *
 */
@XStreamAlias("TableQueryItem")
public class DbTableQueryConfigItem extends DbDataQueryBase implements Serializable {
	public DbTableQueryConfigItem(){
		super();
	}
	
	public DbTableQueryConfigItem(String tableName, String clause) {
		// TODO Auto-generated constructor stub
		super(tableName, clause);
	}
}
