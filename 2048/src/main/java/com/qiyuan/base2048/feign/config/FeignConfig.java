package com.qiyuan.base2048.feign.config;

import com.qiyuan.bautil.util.HttpTool;
import com.qiyuan.bautil.util.JwtUtil;
import com.qiyuan.bautil.util.RequestHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;


@Configuration
@Slf4j
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        /** 传递seata XID **/
        String xid = RootContext.getXID();
        if(StringUtils.isNotBlank(xid)){
            requestTemplate.header(RootContext.KEY_XID,xid);
            log.info("FeignConfig xid={}",xid);
        }else{
            log.info("FeignConfig xid = NULL");
        }
        /**  传递用户信息  **/
        HttpServletRequest request = HttpTool.getHttpServletRequest();
        String jsonEncString = null;
        if(request == null) {
            log.info("FeignConfig request = NULL");
        }else{
            jsonEncString = request.getHeader(JwtUtil.AUTHORIZE_USER);
            log.info("FeignConfig request = OBJECT");
            log.info("FeignConfig jsonEncString from request = {}",jsonEncString);
        }

        if(StringUtils.isBlank(jsonEncString)){
            log.error("未获取到request中的user信息。");
            log.info("当前线程号======{}",Thread.currentThread().getId());
            /** 适用多线程从request中取不到的情况 **/
            Object str = RequestHolder.get(JwtUtil.AUTHORIZE_USER);
            if (str == null) {
                log.info("FeignConfig jsonEncString from ThreadLocal = NULL");
            } else {
                jsonEncString = (String) str;
                log.info("FeignConfig jsonEncString from ThreadLocal = {}",jsonEncString);
            }
        }

        requestTemplate.header(JwtUtil.AUTHORIZE_USER, jsonEncString);
    }
}
