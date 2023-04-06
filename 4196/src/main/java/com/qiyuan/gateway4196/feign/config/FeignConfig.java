package com.qiyuan.gateway4196.feign.config;


import com.qiyuan.bautil.util.TenantContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-26 16:54
 **/
@Configuration
@Slf4j
public class FeignConfig implements RequestInterceptor {
    @Value("${gateway4196.tenant.default}")
    private String defaultTenantId;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TenantContextHolder.TENANT_ID, defaultTenantId);
    }
}
