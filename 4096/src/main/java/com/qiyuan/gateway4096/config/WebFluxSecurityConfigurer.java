package com.qiyuan.gateway4096.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @Description EnableWebFluxSecurity权限验证配置
 */
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurityConfigurer {

    /**
     * http请求路径权限与过滤链配置
     */
    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        log.info("加载security 权限配置....");
        http.httpBasic().

                and().
                authorizeExchange().
                pathMatchers("/actuator/**").
                authenticated().
                anyExchange().
                permitAll().

                and().
                csrf().
                disable();

        return http.build();
    }
}
