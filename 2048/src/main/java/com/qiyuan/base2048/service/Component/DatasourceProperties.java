package com.qiyuan.base2048.service.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("DatasourceProperties")
public class DatasourceProperties {
    @Value("${spring.datasource.driver-class-name}")
    private String tmpDriverClassName;
    @Value("${spring.datasource.url}")
    private String tmpUrl;
    @Value("${spring.datasource.username}")
    private String tmpUser;
    @Value("${spring.datasource.password}")
    private String tmpPassword;

    public static String driverClassName;
    public static String url;
    public static String user;
    public static String password;

    @PostConstruct
    private void init(){
        driverClassName = tmpDriverClassName;
        url = tmpUrl;
        user = tmpUser;
        password = tmpPassword;
    }
    public static String getDriverClassName() {
        return driverClassName;
    }
    public static String getUrl(){
        return url;
    }
    public static String getUser(){
        return user;
    }
    public static String getPassword(){
        return password;
    }
}
