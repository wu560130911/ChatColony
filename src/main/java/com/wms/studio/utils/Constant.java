/**
 * 
 */
package com.wms.studio.utils;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:31:53
 */
public class Constant {

	/**
	 * 错误状态返回
	 */
	public static final int ERROR = Integer.MIN_VALUE;
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	/**
	 * 默认只能处理128以一下的密钥长度
	 */
	public static final int AES_KEY_SIZE = 128;
}
