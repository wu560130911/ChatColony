/**
 * 
 */
package com.wms.studio.main;

import java.util.List;

import org.apache.log4j.Logger;

import com.wms.studio.exception.ContainerNotFoundException;
import com.wms.studio.spi.Container;
import com.wms.studio.utils.SystemProps;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午5:00:12
 */
public class Start {

	public static final String SYSTEM_NAME = "chat.server.name";

	private static final Logger logger = Logger.getLogger(Start.class);

	private static volatile boolean running = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		if (!SystemProps.InitProps()) {
			System.err.println("系统配置文件丢失,系统启动失败.");
			System.exit(1);
		}
		System.out.println(SystemProps.getValue(SYSTEM_NAME) + "系统开始启动中...");
		System.out.println("系统启动时间:"
				+ SystemProps.getDateFormatString(startTime));
		List<Container> containers = null;
		try {
			containers = SystemProps.getContainer();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ContainerNotFoundException e) {
			e.printStackTrace();
		}

		if (containers == null) {
			System.exit(1);
		}
		final List<Container> containerss = containers;
		for (Container container : containers) {
			container.start();
			logger.info("Chat-Server" + container.getClass().getSimpleName()
					+ " started!");
		}

		long endTime = System.currentTimeMillis();
		System.out.println("系统启动完成时间:"
				+ SystemProps.getDateFormatString(endTime));
		System.out.println("系统启动完成,总耗时:" + (endTime - startTime) + "ms.");

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				for (Container container : containerss) {
					try {
						container.stop();
						logger.info("Chat-Server:"
								+ container.getClass().getSimpleName()
								+ " stopped!");
					} catch (Throwable t) {
						logger.error(t.getMessage(), t);
					}
					synchronized (Start.class) {
						running = false;
						Start.class.notify();
					}
				}
			}
		});

		synchronized (Start.class) {
			while (running) {
				try {
					Start.class.wait();
				} catch (Throwable e) {
				}
			}
		}
	}

}
