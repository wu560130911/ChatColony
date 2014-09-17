/**
 * 
 */
package com.wms.studio.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.wms.studio.exception.ContainerNotFoundException;
import com.wms.studio.spi.Container;

/**
 * @author WMS
 * 
 */
public class SystemProps {

	private static Logger log = Logger.getLogger(SystemProps.class);

	private static Properties properties = null;

	public static final String SERVER_FILE = "server.properties";

	public static final String CHAT_SERVER_CONTAINER_FILE = "META-INF/Container/container.properties";

	public static HashMap<String, String> containerHashMap = new HashMap<String, String>();

	public static final String CHAT_SERVER_CONTAINER_KEY = "chat.server.container";

	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);

	/**
	 * 初始化系统配置文件，如果初始化失败，系统将停止运行
	 * 
	 * @return 系统配置文件是否初始化成功
	 */
	public static boolean InitProps() {

		log.info("初始化系统配置文件");
		/**
		 * 防止再次加载
		 */
		if (properties != null) {
			return true;
		}

		try {
			properties = PropertiesLoaderUtils.loadAllProperties(SERVER_FILE);
			initContainerClass();
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("加载配置文件失败", e);
			}
			return false;
		}

		return true;
	}

	/**
	 * 
	 * 获取系统指定键的值
	 * 
	 * @param key
	 *            键
	 * @return 指定键的值
	 */
	public static String getValue(String key) {

		return getValue(key, null);
	}

	/**
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 指定键的值
	 */
	public static String getValue(String key, String defaultValue) {
		return properties == null ? null : properties.getProperty(key,
				defaultValue);
	}

	public static int getIntergetValue(String key) {

		String value = getValue(key);

		if (value == null) {
			return Constant.ERROR;
		}

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return Constant.ERROR;
		}
	}

	/**
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return 是否设置成功
	 */
	public static boolean setValue(String key, String value) {

		return properties == null ? false
				: properties.setProperty(key, value) == null ? false : true;
	}

	public static String getProperty(String key) {

		return getValue(key);
	}

	private static void initContainerClass() throws IOException {

		Properties properties = PropertiesLoaderUtils
				.loadAllProperties(CHAT_SERVER_CONTAINER_FILE);

		if (properties == null) {
			throw new IOException();
		}

		Iterator<Entry<Object, Object>> values = properties.entrySet()
				.iterator();

		while (values.hasNext()) {
			Entry<Object, Object> value = values.next();
			containerHashMap.put((String) value.getKey(),
					(String) value.getValue());
		}

	}

	public static List<Container> getContainer()
			throws ContainerNotFoundException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		String containerValues = getValue(CHAT_SERVER_CONTAINER_KEY, null);

		if (containerValues == null) {
			return Collections.emptyList();
		}

		String[] values = containerValues.split(",");

		List<Container> containers = new ArrayList<Container>();

		for (String key : values) {
			String clazz = containerHashMap.get(key);
			if (clazz == null) {
				throw new ContainerNotFoundException("指定" + key
						+ "的Container不存在,请确认您的配置.");
			}
			Container container = (Container) Class.forName(clazz)
					.newInstance();
			containers.add(container);
		}
		return containers;
	}

	public static String getDateFormatString(Date date) {
		return sdf.format(date);
	}

	public static String getDateFormatString(long times) {
		return sdf.format(times);
	}
}
