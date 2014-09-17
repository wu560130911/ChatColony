/**
 * 
 */
package com.wms.studio.container;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wms.studio.spi.Container;
import com.wms.studio.utils.SystemProps;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月13日 下午2:45:31
 */
public class SpringContainer implements Container {

	private static final Logger logger = Logger.getLogger(SpringContainer.class);

    public static final String SPRING_CONFIG = "chat.spring.config";
    
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:applicationContext.xml";

    static ClassPathXmlApplicationContext context;
    
    public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
        String configPath = SystemProps.getProperty(SPRING_CONFIG);
        if (configPath == null || configPath.length() == 0) {
            configPath = DEFAULT_SPRING_CONFIG;
        }
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
    }

    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

}
