package com.wms.studio.chat.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.studio.chat.ChatServer;
import com.wms.studio.chat.handler.AbstractOperatorHandler;
import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.model.ServerHistory;

public class ServerJoinHandler extends AbstractOperatorHandler {

	private List<ServerHistory> onlineServers;

	private static final String responseCode = "403";

	public boolean initHandler() {

		return true;
	}

	public void execute(TransferProtocol tp, OnlineServer os) {

		onlineServers = new ArrayList<ServerHistory>(
				ChatServer.onlineServerModels.values());

	}

	public String getHandlerName() {

		return ServerJoinHandler.class.getSimpleName();
	}

	public TransferProtocol getResponse() {

		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
		
		TransferProtocol response = new TransferProtocol(responseCode);
		
		response.setMessage(JSON.toJSONString(onlineServers,SerializerFeature.WriteDateUseDateFormat));
		
		return response;
	}

}
