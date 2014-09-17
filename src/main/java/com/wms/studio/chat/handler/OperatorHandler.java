/**
 * 
 */
package com.wms.studio.chat.handler;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.model.OnlineServer;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午12:13:14
 */
public interface OperatorHandler {
	
	public boolean initHandler();

	public void execute(TransferProtocol tp,OnlineServer os);

	public void setApplicationContext(ClassPathXmlApplicationContext ac);
	
	public String getHandlerName();
	
	public TransferProtocol getResponse();
	
	public boolean isEncrypt();
}
