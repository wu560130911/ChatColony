/**
 * 
 */
package com.wms.studio.chat.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.wms.studio.chat.ChatServer;
import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.container.ChatContainer;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.security.utils.Cryptos;
import com.wms.studio.utils.Encodes;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午6:25:15
 */
public class ChatThreadHandler extends Thread {

	private static final Logger logger = Logger
			.getLogger(ChatThreadHandler.class);

	private Long id;

	private OnlineServer os;

	private Socket socket;

	private BufferedReader reader;

	private PrintStream ps;

	private static final String SERVER_JOIN_HANDLER_CODE = "303";

	private static final String SERVER_EXIT_HANDLER_CODE = "304";

	private static final String SERVER_ECHO_HANDLER_CODE = "302";

	public ChatThreadHandler(OnlineServer os) {
		this.os = os;
		this.socket = os.getSocket();
		this.id = os.getServerHistory().getId();
		setName("ChatServer-" + id + "-Thread-" + super.getId());
		logger.info("创建编号为" + id + "的服务器，并开启线程");
		try {
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));// 获取客户端传来的信息
			ps = new PrintStream(socket.getOutputStream());// 写信息到客户端
			logger.info("创建输入输出流");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		logger.info("服务器编号为" + id + "的线程开始");

		sendValidation();

		// 需要一次广播
		updateOnlineServers();

		while (true) {

			if (reader == null) {
				// TODO 处理异常
				Close();
			}

			String message = null;

			try {
				message = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO 处理异常
				Close();

			} catch (Exception e) {
				e.printStackTrace();
				Close();
			}

			if (message == null) {
				// TODO 处理异常
				Close();
				return;
			}
			System.out.println("WMS：" + message);
			message = message.trim();

			if (message.length() < 3) {
				continue;
			}

			message = Cryptos.aesDecrypt(Encodes.decodeBase64(message),
					Encodes.decodeBase64(os.getSERVER_DES_KEY()));

			TransferProtocol tp = JSON.parseObject(message,
					TransferProtocol.class);

			if (tp == null) {
				continue;
			}

			String keyCode = tp.getProtocolCode();

			// 根据tp的协议号进行判断服务类型

			if (keyCode == null || "".equals(keyCode)) {
				continue;
			}

			OperatorHandler handler = ChatContainer.handlers.get(keyCode);

			if (handler != null) {

				handler.execute(tp, os);

				TransferProtocol response = handler.getResponse();

				sendMessage(response, false, handler.isEncrypt());

			}

		}
	}

	public void sendMessage(TransferProtocol response, boolean broadcast,
			boolean isEncrypt) {
		if (response != null) {

			String responseString = JSON.toJSONString(response);

			if (broadcast) {

				Collection<ChatThreadHandler> onlineServerThreads = ChatServer.onlineServerThreads
						.values();

				for (ChatThreadHandler cth : onlineServerThreads) {
					if (cth != null) {
						cth.sendMessage(responseString, isEncrypt);
					}
				}

			} else {
				sendMessage(responseString, isEncrypt);
			}

		}
	}

	public void sendMessage(TransferProtocol response, boolean isEncrypt) {
		sendMessage(response, false, isEncrypt);
	}

	public void sendMessage(String responseText, boolean isEncrypt) {

		if (responseText != null && ps != null) {

			System.out.println(responseText);

			if (isEncrypt && os.getSERVER_DES_KEY() != null) {
				responseText = Encodes.encodeBase64(Cryptos.aesEncrypt(
						responseText.getBytes(),
						Encodes.decodeBase64(os.getSERVER_DES_KEY())));
			}

			ps.println(responseText);
			ps.flush();
		}

	}

	public void updateOnlineServers() {
		OperatorHandler handler = ChatContainer.handlers
				.get(SERVER_JOIN_HANDLER_CODE);

		if (handler != null) {
			handler.execute(null, null);
			TransferProtocol response = handler.getResponse();
			sendMessage(response, true, handler.isEncrypt());
		}
	}

	public void sendValidation() {
		OperatorHandler handler = ChatContainer.handlers
				.get(SERVER_ECHO_HANDLER_CODE);

		if (handler != null) {
			handler.execute(null, os);
			TransferProtocol response = handler.getResponse();
			sendMessage(response, true, handler.isEncrypt());
		}
	}

	protected void Close() {

		OperatorHandler handler = ChatContainer.handlers
				.get(SERVER_EXIT_HANDLER_CODE);

		if (handler != null) {
			handler.execute(null, os);
			TransferProtocol response = handler.getResponse();
			sendMessage(response, true, handler.isEncrypt());
		}

		if (reader != null) {
			try {
				reader.close();
				reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (ps != null) {
			ps.close();
			ps = null;
		}

		if (socket != null) {
			try {
				socket.close();
				socket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
