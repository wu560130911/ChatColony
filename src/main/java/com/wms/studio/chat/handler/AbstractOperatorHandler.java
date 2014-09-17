package com.wms.studio.chat.handler;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractOperatorHandler implements OperatorHandler {

	protected ClassPathXmlApplicationContext context;

	public void setApplicationContext(ClassPathXmlApplicationContext ac) {
		this.context = ac;
	}
	
	public boolean isEncrypt() {
		return true;
	}

}
