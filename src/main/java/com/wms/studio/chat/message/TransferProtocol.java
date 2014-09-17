/**
 * 
 */
package com.wms.studio.chat.message;

import java.io.Serializable;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午2:47:36
 */
@SuppressWarnings("serial")
public class TransferProtocol implements Serializable{

	private String protocolCode;//协议编号
	
	private String serverId;//服务器端编号
	
	private String clientId;//客户端编号
	
	private String message;//协议数据

	public TransferProtocol() {
	}
	
	public TransferProtocol(String protocolCode) {
		this.protocolCode = protocolCode;
	}
	
	/**
	 * @return the protocolCode
	 */
	public String getProtocolCode() {
		return protocolCode;
	}

	/**
	 * @param protocolCode the protocolCode to set
	 */
	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
