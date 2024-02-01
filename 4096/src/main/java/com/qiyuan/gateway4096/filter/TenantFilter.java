package com.qiyuan.gateway4096.filter;


import com.qiyuan.bautil.util.TenantContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * 处理租户问题
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-26 10:11
 **/
@Component
public class TenantFilter implements GlobalFilter, Ordered {
    /**
     * 一个网关只为一个租户服务，租户ID放在配置文件里
     * 前端指向哪个网关，就属于哪个租户使用
     */
    @Value("${gateway4096.tenant.default}")
    private String defaultTenantId;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set(TenantContextHolder.TENANT_ID, defaultTenantId);
        };
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
