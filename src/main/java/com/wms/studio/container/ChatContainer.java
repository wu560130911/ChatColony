/**
 * 
 */
package com.wms.studio.container;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.wms.studio.chat.ChatServer;
import com.wms.studio.chat.handler.OperatorHandler;
import com.wms.studio.spi.Container;
import com.wms.studio.utils.SystemProps;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:54:51
 */
public class ChatContainer implements Container {

	private static final Logger logger = Logger.getLogger(ChatContainer.class);

	public static final String SERVER_PORT = "chat.server.port";

	public static final String CHAT_SERVER_CODE = "com/wms/studio/chat/handler/handler.properties";

	private static ChatServer chatServer;

	public static HashMap<String, OperatorHandler> handlers = new HashMap<String, OperatorHandler>();

	public void start() {

		logger.info("初始化ChatContainer...");

		if (!initHandlerClass()) {
			logger.error("初始化系统配置文件失败,请检查");
			return;
		}

		int port = SystemProps.getIntergetValue(SERVER_PORT);

		chatServer = new ChatServer(port);

		chatServer.start();

	}

	public void stop() {

		if (chatServer != null) {
			chatServer.destroy();
		}
	}

	private boolean initHandlerClass() {

		Properties properties = null;
		try {
			properties = PropertiesLoaderUtils
					.loadAllProperties(CHAT_SERVER_CODE);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (properties == null) {
			return false;
		}

		Iterator<Entry<Object, Object>> values = properties.entrySet()
				.iterator();

		while (values.hasNext()) {

			Entry<Object, Object> value = values.next();
			String keyCode = (String) value.getKey();
			String clazz = (String) value.getValue();

			if (keyCode == null || clazz == null || "".equals(clazz)
					|| "".equals(keyCode)) {
				continue;
			}

			try {
				OperatorHandler handler = (OperatorHandler) (Class
						.forName(clazz)).newInstance();
				if (handler != null) {
					handlers.put(keyCode, handler);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		for (OperatorHandler handler : handlers.values()) {
			logger.info("初始化处理器:"+handler.getHandlerName());
			handler.setApplicationContext(SpringContainer.getContext());
			if (!handler.initHandler()) {
				logger.error(handler.getHandlerName()+"初始化失败,程序结束运行");
				return false;
			}
		}

		return true;

	}

}
