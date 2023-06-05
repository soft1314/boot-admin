package com.qiyuan.log6144.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiyuan.bautil.dto.LogDTO;
import com.qiyuan.log6144.service.LogSaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/log/free/collector")
@Slf4j
public class LogCollectController {
    @Resource
    private LogSaver logSaver;

    @RequestMapping("/send")
    public void send(HttpServletRequest request) throws Exception {
        int contentLength = request.getContentLength();
        byte[] contentBytes = new byte[contentLength];
        request.getInputStream().read(contentBytes);
        String jsonStr = new String(contentBytes);
        ObjectMapper mapper = new ObjectMapper();
        LogDTO logDTO = mapper.readValue(jsonStr, LogDTO.class);
        logSaver.saveLog(logDTO);
    }
}
