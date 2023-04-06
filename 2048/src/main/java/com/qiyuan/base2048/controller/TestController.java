package com.qiyuan.base2048.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/system/free/test")
public class TestController {
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
}
