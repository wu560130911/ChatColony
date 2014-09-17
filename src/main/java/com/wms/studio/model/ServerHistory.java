/**
 * 
 */
package com.wms.studio.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:28:38
 */
@SuppressWarnings("serial")
@Entity
public class ServerHistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String ipAddress;

	private int port;

	@Temporal(TemporalType.TIMESTAMP)
	private Date onlineDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	@PrePersist
	public void prePersist() {
		this.onlineDate = new Date();
	}
}
