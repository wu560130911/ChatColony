package com.wms.studio.chat.handler.impl;

import com.wms.studio.chat.handler.AbstractOperatorHandler;
import com.wms.studio.chat.message.TransferProtocol;
import com.wms.studio.model.OnlineServer;
import com.wms.studio.security.utils.Cryptos;
import com.wms.studio.utils.Constant;
import com.wms.studio.utils.Encodes;

/**
 * 
 * @author WMS
 * @version 1.0
 * @date 2014年9月15日 上午10:11:54
 */
public class EchoHandler extends AbstractOperatorHandler {

	private OnlineServer os;
	
	private static final String responseCode="402";
	
	public boolean initHandler() {

		return true;
	}

	public void execute(TransferProtocol tp,OnlineServer os) {
		this.os = os;
		byte[] saltbytes = Cryptos.generateAesKey(Constant.AES_KEY_SIZE);
		os.setSERVER_DES_KEY(Encodes.encodeBase64(saltbytes));
	}

	public String getHandlerName() {

		return EchoHandler.class.getSimpleName();
	}

	public TransferProtocol getResponse() {

		TransferProtocol response = new TransferProtocol(responseCode);
		response.setServerId(os.getServerHistory().getId().toString());
		response.setMessage(os.getSERVER_DES_KEY());
		
		return response;
	}

	@Override
	public boolean isEncrypt() {

		return false;
	}

}
