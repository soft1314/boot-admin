package com.qiyuan.bautil.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.cloud.openfeign.FeignContext;
@Component
public class SpringTool implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringTool.applicationContext == null) {
            SpringTool.applicationContext = applicationContext;
        }
    }
    public static void set(ApplicationContext applicationContext) {
        SpringTool.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        if(getApplicationContext() == null){
            return null;
        }
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

//    public static <T> T getBean(String name, Class<T> clazz) {
//        return getApplicationContext().getBean(name, clazz);
//    }

    public static <T> T getBean(String beanName, Class<T> tClass) {
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(beanName, tClass);
    }

    public static <T> T getFeignBean(String beanName, Class<T> tClass) {
        if(applicationContext == null){
            return null;
        }
        FeignContext feignContext = applicationContext.getBean("feignContext", FeignContext.class);
        if(feignContext == null){
            return null;
        }
        return feignContext.getInstance(beanName, tClass);
    }
}