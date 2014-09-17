/**
 * 
 */
package com.wms.studio.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午3:04:47
 */
@SuppressWarnings("serial")
public class OnlineUser implements Serializable {

	private User user;

	private String ipAddress;

	private int port;

	private Long serverId;

	private String id;

	private Date joinDate;

	public User getUser() {

		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if (user != null) {
			this.id = user.getId();
		}
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

}
