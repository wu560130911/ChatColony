/**
 * 
 */
package com.wms.studio.model;

import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午3:09:58
 */
public class OnlineServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8140929652044761696L;

	private Socket socket;

	private ServerHistory serverHistory;
	
	private ConcurrentHashMap<String, OnlineUser> onlineUsers = new ConcurrentHashMap<String, OnlineUser>();
	
	private String SERVER_DES_KEY=null;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ServerHistory getServerHistory() {
		return serverHistory;
	}

	public void setServerHistory(ServerHistory serverHistory) {
		this.serverHistory = serverHistory;
	}

	public OnlineServer(Socket socket, ServerHistory serverHistory) {
		this.serverHistory = serverHistory;
		this.socket = socket;
	}

	public ConcurrentHashMap<String, OnlineUser> getOnlineUsers() {
		return onlineUsers;
	}
	
	public void addOnlineUser(OnlineUser os){
		this.onlineUsers.put(os.getUser().getId(), os);
	}
	
	public void removeOnlineUser(String key){
		if(this.onlineUsers.contains(key)){
			this.onlineUsers.remove(key);
		}
	}
	
	public OnlineServer() {

	}

	public void setOnlineUsers(ConcurrentHashMap<String, OnlineUser> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public String getSERVER_DES_KEY() {
		return SERVER_DES_KEY;
	}

	public void setSERVER_DES_KEY(String sERVER_DES_KEY) {
		SERVER_DES_KEY = sERVER_DES_KEY;
	}
	
	

}
