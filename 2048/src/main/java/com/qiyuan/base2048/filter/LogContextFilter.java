package com.qiyuan.base2048.filter;

import com.qiyuan.bautil.util.StringUtil;
import com.qiyuan.bautil.util.UserTool;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogContextFilter extends OncePerRequestFilter {
    private static final String UID = "Uid";
    private static final String PARENT_ID="parentId";
    private static final String APP_NAME="appName";
    private static final String PARENT_ID_KEY_IN_HEADER="x-b3-parentspanid";


    @Value("${spring.application.name}")
    private String appName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String operator = UserTool.getOperator();
        /**
         * 将用户标识放入日志上下文中
         */
        if(StringUtil.isBlank(operator)){
            MDC.put(UID, "系统");
        }else {
            MDC.put(UID, operator);
        }
        /**
         * 将parentSpanId放在MDC日志上下文
         */
        String parentId = request.getHeader(PARENT_ID_KEY_IN_HEADER);
        MDC.put(PARENT_ID,parentId);
        /**
         * 将application_name放入MDC
         */
        MDC.put(APP_NAME,appName);

        filterChain.doFilter(request, response);
    }
}