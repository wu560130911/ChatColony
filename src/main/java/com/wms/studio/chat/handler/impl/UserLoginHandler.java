package com.wms.studio.chat.handler.impl;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.wms.studio.chat.ChatServer;
import com.wms.studio.chat.handler.AbstractOperatorHandler;
import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.model.OnlineUser;
import com.wms.studio.model.User;
import com.wms.studio.service.UserService;

/**
 * 
 * @author WMS
 * @version 1.0
 * @date 2014年9月15日 上午9:18:07
 */
public class UserLoginHandler extends AbstractOperatorHandler {

	private UserService userService;

	private static final String responseCode = "401";

	private String message = null;

	private OnlineUser onlineUser;

	public boolean initHandler() {

		userService = super.context.getBean("userService", UserService.class);

		if (userService != null) {
			return true;
		}

		return false;
	}

	public void execute(TransferProtocol tp, OnlineServer os) {

		if (tp == null || tp.getMessage() == null) {
			message = "格式错误";
			return;
		}

		onlineUser = JSON.parseObject(tp.getMessage(), OnlineUser.class);

		if (onlineUser == null) {
			message = "格式错误";
			return;
		}

		User user = onlineUser.getUser();

		if (user != null) {

			if (ChatServer.onlineUsers.containsKey(user.getId())) {
				message = "该用户已经登录";
				return;
			}

			User nuser = this.userService.findById(user.getId());
			if (nuser != null) {

				user.setSalt(nuser.getSalt());

				user.prePersist();

				if (nuser.getPassword().equals(user.getPassword())) {

					message = "正确";
					// 业务逻辑处理

					onlineUser.setUser(nuser);

					onlineUser.setServerId(os.getServerHistory().getId());

					onlineUser.setJoinDate(new Date());
					os.getOnlineUsers().put(user.getId(), onlineUser);
					ChatServer.onlineUsers.put(user.getId(), onlineUser);

				} else {
					message = "密码错误";
				}

				return;
			}
		}
		message = "不存在此用户";

	}

	public String getHandlerName() {

		return UserLoginHandler.class.getSimpleName();
	}

	public TransferProtocol getResponse() {

		TransferProtocol response = new TransferProtocol(responseCode);

		response.setMessage(message);

		return response;
	}

}
