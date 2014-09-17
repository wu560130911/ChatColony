/**
 * 
 */
package com.wms.studio.chat.listener;

import com.wms.studio.chat.message.TransferProtocol;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月16日 下午3:48:02
 */
public interface ServerListener {

	public TransferProtocol onAddServerAction();
	
	public TransferProtocol onRemoveServerAction();
}
