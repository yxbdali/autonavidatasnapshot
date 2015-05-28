/**
 * 
 */
package com.autonavi.data.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

import com.autonavi.test.yxb.lib.XmlSerializeUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverters;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider.Visitor;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author xiangbin.yang
 *
 */
@XStreamAlias("DataFlowQuery")
public class DbDataFlowQueryConfigItem implements Serializable {

	public DbDataFlowQueryConfigItem() {
		// TODO Auto-generated constructor stub
	}

	public DbDataFlowQueryConfigItem(ArrayList<DbTaskQueryConfigItem> taskDataQueryConfigList) {
		this();
		this.taskDataQueryConfigList = taskDataQueryConfigList;
	}

	private String dataFlowType;

	/**
	 * Get data flow type
	 * 
	 * @return
	 */
	public String getDataFlowType() {
		return dataFlowType;
	}

	/**
	 * Set data flow type
	 * 
	 * @param dataFlowType
	 */
	public void setDataFlowType(String dataFlowType) {
		this.dataFlowType = dataFlowType;
		getDataFlowProperty().set(dataFlowType);
	}

	private transient SimpleStringProperty dataFlowProperty;

	/**
	 * Get data flow property
	 * 
	 * @return
	 */
	public SimpleStringProperty getDataFlowProperty() {
		if (dataFlowProperty == null) {
			dataFlowProperty = new SimpleStringProperty(dataFlowType);
		}
		return dataFlowProperty;
	}

	@XStreamImplicit(itemFieldName = "TaskQueryItem")
	private ArrayList<DbTaskQueryConfigItem> taskDataQueryConfigList;

	/**
	 * Get Data flow task data qurery config list
	 * 
	 * @return
	 */
	public ArrayList<DbTaskQueryConfigItem> getTaskDataQueryConfigList() {
		if (taskDataQueryConfigList == null) {
			taskDataQueryConfigList = new ArrayList<>();
		}

		return taskDataQueryConfigList;
	}

	/**
	 * Set Data flow task data qurery config list
	 * 
	 * @param taskDataQueryConfigList
	 */
	public void setTaskDataQueryConfigList(ArrayList<DbTaskQueryConfigItem> taskDataQueryConfigList) {
		this.taskDataQueryConfigList = taskDataQueryConfigList;
	}

	/**
	 * Serialize Data flow query config data to xml file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void serialize(String file) throws IOException {
		XmlSerializeUtils.SaveToXml(this, file);
	}

	/**
	 * Deserialize data flow query config data from xml file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static DbDataFlowQueryConfigItem deserialize(String file) throws IOException {
		return (DbDataFlowQueryConfigItem) XmlSerializeUtils.deserialze(file);
	}

	/**
	 * Serialize to xml file using xtream
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void xstreamSerialize(String file) throws FileNotFoundException, IOException {
		/*
		XStream xStream = new XStream(new XppDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = false;

					@Override
					public void startNode(String name) {
						super.startNode(name);
						cdata = name.equalsIgnoreCase("clause");
					}

					@Override
					protected void writeText(QuickWriter writer, String text) {
						if (cdata && !text.isEmpty()) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		*/
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		try (OutputStream out = new FileOutputStream(file); 
				OutputStreamWriter writer = new OutputStreamWriter(out, Charset.forName("UTF-8").newEncoder())) {
			xStream.toXML(this, writer);
			
		}
	}

	/**
	 * Deserialize from given file
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static DbDataFlowQueryConfigItem xstreamDeserialize(String file) throws FileNotFoundException, IOException {
		DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = null;
		XStream xStream = new XStream();
		xStream.processAnnotations(DbDataFlowQueryConfigItem.class);
		try (InputStream in = new FileInputStream(file); 
				InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8").newDecoder())) {
			dbDataFlowQueryConfigItem = (DbDataFlowQueryConfigItem) xStream.fromXML(reader);
		}
		return dbDataFlowQueryConfigItem;
	}
}
