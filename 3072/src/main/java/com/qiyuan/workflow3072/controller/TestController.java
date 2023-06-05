package com.qiyuan.workflow3072.controller;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/system/free/test")
public class TestController {
    @Resource
    private FormValidator formValidator;

    @GetMapping("/test1")
    public ResultDTO test1(HttpServletRequest request) throws Exception{
        String uaStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentParser.parse(uaStr);

        System.out.println(userAgent.getBrowser());
        System.out.println(userAgent.getVersion());

        System.out.println(userAgent.getOs());
        System.out.println(userAgent.getOsVersion());

        System.out.println(userAgent.getEngine());
        System.out.println(userAgent.getEngineVersion());

        System.out.println(userAgent.getPlatform());

        return ResultDTO.success();
    }


    @GetMapping("/test4")
    public String test4(@RequestParam("level") int level,@RequestParam("msg") String msg){
        MDC.put("uid", "系统");
//        ThreadContext.put("myKey", "myValue");
        switch (level){
            case 1:
                log.trace("这是trace消息：{}",msg);
                break;
            case 2:
                log.debug("这是debug消息：{}",msg);
                break;
            case 3:
                log.info("这是info消息：{}",msg);
                break;
            case 4:
                log.warn("这是warn消息：{}",msg);
                break;
            case 5:
                log.error("这是error消息：{}",msg);
                break;
            default:
                log.error("这是什么消息：{}",msg);
        }
        return "消息已打印";
    }
}
