package com.wms.studio.chat.handler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.studio.chat.ChatServer;
import com.wms.studio.chat.handler.AbstractOperatorHandler;
import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.model.OnlineUser;
import com.wms.studio.model.ServerHistory;
import com.wms.studio.service.ServerHistoryService;

public class ServerExitHandler extends AbstractOperatorHandler {

	private ServerHistoryService serverHistoryService;

	private List<ServerHistory> onlineServers;

	private static final String responseCode = "404";

	public boolean initHandler() {

		serverHistoryService = super.context.getBean("serverHistoryService",
				ServerHistoryService.class);

		if (serverHistoryService != null) {
			return true;
		}

		return false;
	}

	public void execute(TransferProtocol tp, OnlineServer os) {

		// 需要下线的用户
		Collection<OnlineUser> users = os.getOnlineUsers().values();

		for (OnlineUser ou : users) {
			ChatServer.onlineUsers.remove(ou.getId());
		}

		this.serverHistoryService.delete(os.getServerHistory());
		ChatServer.removeOnlineServer(os.getServerHistory().getId());

		onlineServers = new ArrayList<ServerHistory>(
				ChatServer.onlineServerModels.values());
	}

	public String getHandlerName() {

		return ServerExitHandler.class.getSimpleName();
	}

	public TransferProtocol getResponse() {

		TransferProtocol response = new TransferProtocol(responseCode);

		response.setMessage(JSON.toJSONString(onlineServers,
				SerializerFeature.WriteDateUseDateFormat));

		return response;
	}

}
