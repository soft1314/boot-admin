package com.qiyuan.base2048.aspect;

import com.qiyuan.bautil.util.IpAddrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//@Aspect
@Component
@Slf4j
@Deprecated
public class LogAspectController {
//    @Autowired
//    private ITbLogGeneralService tbLogGeneralService;

    @Pointcut("execution(* com.qiyuan.base2048.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Exception{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String ip = IpAddrUtil.getIpAddr(request);//request.getRemoteAddr();

        String method =
                joinPoint.getSignature().getDeclaringTypeName()
                        + "." +
                        joinPoint.getSignature().getClass();

        Object[] args = joinPoint.getArgs();

        content content = new content(url, ip, method, args);
        log.debug("Request:{}", content);

//        tbLogGeneralService.createLog(content.toString());
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void logAfterReturning(Object result) throws Exception{
        log.debug("Result:{}", result);
        if(result == null) {
//            tbLogGeneralService.createLog("Result:NULL");
        }else {
//            tbLogGeneralService.createLog("Result:" + result.toString());
        }
    }


    /**
     * 我们想让日志记录 :
     * url请求
     * 访问对象的IP地址
     * 调用了什么方法
     * 传入了什么参数
     */
    private class content {
        private String url;
        private String ip;
        private String method;
        private Object[] args;

        public content(String url, String ip, String method, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.method = method;
            this.args = args;
        }

        @Override
        public String toString() {
            return "Request:{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", method='" + method + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
