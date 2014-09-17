/**
 * 
 */
package com.wms.studio.exception;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午4:44:46
 */
@SuppressWarnings("serial")
public class ContainerNotFoundException extends Exception{

	public ContainerNotFoundException() {
		super("指定Container不存在.");
	}
	
	public ContainerNotFoundException(String message) {
		super(message);
	}
	
	public ContainerNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}
	
	public ContainerNotFoundException(Throwable cause) {
		super(cause);
	}
}
