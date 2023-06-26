package com.qiyuan.base2048.service.component;

import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GetBeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (GetBeanUtils.applicationContext == null) {
            GetBeanUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

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
