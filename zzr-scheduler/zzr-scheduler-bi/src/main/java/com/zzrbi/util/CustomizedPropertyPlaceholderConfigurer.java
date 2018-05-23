package com.zzrbi.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties文件占位符配置
 * @author sihang
 *
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private static Map<String,Object> contextPropertiesMap;
	
	public static Object getContextProperty(String key) {
		return contextPropertiesMap.get(key);
	}
	
	@Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        
        contextPropertiesMap = new HashMap<String, Object>();
        
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            contextPropertiesMap.put(keyStr, value);
        }
    }

}
