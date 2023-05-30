package com.qiyuan.base2048.filter;

import com.qiyuan.bautil.util.UserTool;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
public class OperatorLogFilter extends OncePerRequestFilter {
    private static final String UID = "Uid";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String operator = UserTool.getOperator();
        /**
         * 将用户标识放入日志上下文中
         */
        MDC.put("uid", operator);
        filterChain.doFilter(request, response);
    }
}
