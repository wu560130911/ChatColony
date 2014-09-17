/**
 * 
 */
package com.wms.studio.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.wms.studio.chat.handler.ChatThreadHandler;
import com.wms.studio.container.SpringContainer;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.model.OnlineUser;
import com.wms.studio.model.ServerHistory;
import com.wms.studio.service.ServerHistoryService;
import com.wms.studio.utils.NamedThreadFactory;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:59:51
 */
public class ChatServer {

	private static final Logger logger = Logger.getLogger(ChatServer.class);

	private static int DEFAULT_PORT = 8084;

	private int port;

	public static ConcurrentHashMap<Long, OnlineServer> onlineServers = new ConcurrentHashMap<Long, OnlineServer>();

	public static ConcurrentHashMap<Long, ServerHistory> onlineServerModels = new ConcurrentHashMap<Long, ServerHistory>();
	
	public static ConcurrentHashMap<Long, ChatThreadHandler> onlineServerThreads = new ConcurrentHashMap<Long, ChatThreadHandler>();

	public static ConcurrentHashMap<String, OnlineUser> onlineUsers = new ConcurrentHashMap<String, OnlineUser>();

	private ServerSocket server = null;

	// 统计信息收集定时器
	private ScheduledFuture<?> sendFuture;
	
	// 定时任务执行器
	private final ScheduledExecutorService scheduledExecutorService = Executors
			.newScheduledThreadPool(3, new NamedThreadFactory("ChatServer",
					true));

	private ServerHistoryService serverHistoryService;

	public ChatServer(int port) {

		if (port < 1) {
			this.port = DEFAULT_PORT;
		} else {
			this.port = port;
		}

		serverHistoryService = SpringContainer.getContext().getBean(
				"serverHistoryService", ServerHistoryService.class);

		logger.info("创建ChatServer,使用端口为:" + this.port);
	}

	public void start() {
		try {
			server = new ServerSocket(port);// 初始化服务器套接字
			logger.info("初始化服务器端套接字");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (server == null) {
			logger.info("服务器端套接字初始化失败，请检查");
			return;
		}
		logger.info("启用任务调度器，监听集群的通信。");
		sendFuture = scheduledExecutorService.scheduleWithFixedDelay(
				new Runnable() {

					public void run() {
						try {
							logger.info("等待客户端的连接...");
							Socket socket = server.accept();
							logger.info("接收到客户端:"
									+ socket.getInetAddress().getHostAddress()
									+ ":" + socket.getPort());
							// 封装服务器的连接

							ServerHistory sh = new ServerHistory();
							sh.setIpAddress(socket.getInetAddress()
									.getHostAddress());
							sh.setPort(socket.getPort());

							serverHistoryService.save(sh);

							OnlineServer os = new OnlineServer(socket, sh);

							onlineServers.put(sh.getId(), os);
							onlineServerModels.put(sh.getId(), sh);

							ChatThreadHandler cth = new ChatThreadHandler(os);

							onlineServerThreads.put(sh.getId(), cth);
							
							cth.start();
							
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}, 9, 500, TimeUnit.MILLISECONDS);
	}

	public static void removeOnlineServer(Long key) {
		if (onlineServers != null && onlineServers.containsKey(key)) {
			onlineServers.remove(key);
			onlineServerModels.remove(key);
			onlineServerThreads.remove(key);
		}
	}

	public void destroy() {
		try {
			sendFuture.cancel(true);
			Thread.sleep(3000);
			server.close();
			logger.info("销毁ChatServer,清理任务和服务器套接字");
		} catch (Throwable t) {
			logger.error(
					"Unexpected error occur at cancel timer, cause: "
							+ t.getMessage(), t);
		}
	}

	public int getPort() {
		return port;
	}

}
