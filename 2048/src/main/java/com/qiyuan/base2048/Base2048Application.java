package com.qiyuan.base2048;

import com.qiyuan.base2048.filter.JwtAuthenticationFilter;
import com.qiyuan.base2048.service.component.DatasourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

@SpringBootApplication
//@MapperScan("com.qiyuan.base2048.mapper")
@ComponentScan(basePackages = "com.qiyuan")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qiyuan.base2048.feign")
public class Base2048Application {
    @Resource
    private DatasourceProperties datasourceProperties;

    public static void main(String[] args) {
        SpringApplication.run(Base2048Application.class, args);
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
