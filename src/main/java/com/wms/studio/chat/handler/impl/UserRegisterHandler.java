package com.wms.studio.chat.handler.impl;

import com.wms.studio.chat.handler.AbstractOperatorHandler;
import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.service.UserService;

public class UserRegisterHandler extends AbstractOperatorHandler {

	private UserService userService;

	private static final String responseCode = "400";

	public void execute(TransferProtocol tp,OnlineServer os) {

	}

	public boolean initHandler() {

		userService = super.context.getBean("userService", UserService.class);

		if (userService != null) {
			return true;
		}

		return false;
	}

	public String getHandlerName() {

		return UserRegisterHandler.class.getSimpleName();
	}

	public TransferProtocol getResponse() {

		return null;
	}

}
