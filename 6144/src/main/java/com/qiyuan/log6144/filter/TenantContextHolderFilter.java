package com.qiyuan.log6144.filter;

import com.qiyuan.bautil.util.StringUtil;
import com.qiyuan.bautil.util.TenantContextHolder;
import com.qiyuan.log6144.service.component.MessageUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 租户上下文过滤器
 * @author pangu
 * @date 2020-9-7
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {
    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            //todo
            String tenantId = "DEMO";//request.getHeader(TenantContextHolder.TENANT_ID);
            if (StringUtil.isNotBlank(tenantId)) {
                log.info("tenantId={}-------------获取到的租户ID", tenantId);
                TenantContextHolder.setTenantId(tenantId);
                filterChain.doFilter(request, response);
            }else{
                logger.error("tenantId is blank.--------------未取到租户ID");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageUtils.get("tenant.id.blank.error"));
                return;
            }
        }catch (Exception ex) {
            log.error(ex.getMessage(),ex);
        }finally {
            TenantContextHolder.clear();
        }
    }
}


