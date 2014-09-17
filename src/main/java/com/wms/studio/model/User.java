/**
 * 
 */
package com.wms.studio.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wms.studio.security.utils.Digests;
import com.wms.studio.utils.Constant;
import com.wms.studio.utils.Encodes;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月12日 上午9:08:56
 */
@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877361738602436796L;

	@Id
	private String id;

	@Column(length = 40)
	private String name;

	@Column(length = 100)
	private String password;

	private String ip;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;

	private boolean disable = false;// 是否被锁定

	private String salt;// 加密字符串

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return the disable
	 */
	public boolean isDisable() {
		return disable;
	}

	/**
	 * @param disable
	 *            the disable to set
	 */
	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PrePersist
	public void prePersist() {
		this.registerDate = new Date();
		entryptPassword(this, false);
	}

	private void entryptPassword(User user, boolean flag) {

		byte[] salt = null;
		
		if (flag) {
			salt = Encodes.decodeHex(user.getSalt());
		} else {
			salt = Digests.generateSalt(Constant.SALT_SIZE);
			user.setSalt(Encodes.encodeHex(salt));
		}

		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt,
				Constant.HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
}
