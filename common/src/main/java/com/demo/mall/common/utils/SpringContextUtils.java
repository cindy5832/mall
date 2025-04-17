package com.demo.mall.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// Spring Context 工具
public class SpringContextUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }

    public static boolean containsBean(String beanName) {
        return applicationContext.containsBean(beanName);
    }

    public static boolean isSingleton(String beanName) {
        return applicationContext.isSingleton(beanName);
    }

    public static Class<?> getType(String beanName) {
        return applicationContext.getType(beanName);
    }
}
