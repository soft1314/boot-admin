package com.qiyuan.log6144.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiyuan.bautil.dto.LogDTO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.StringContainerVO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.log6144.service.LogSaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/log/free/collector/custom")
@Slf4j
public class LogCollectForCustomAppenderController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private LogSaver logSaver;

    @PostMapping("/send")
    public ResultDTO send(@RequestBody @Valid StringContainerVO stringContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ObjectMapper mapper = new ObjectMapper();
        LogDTO logDTO = mapper.readValue(stringContainerVO.getStrValue(), LogDTO.class);
        logSaver.saveLog(logDTO);
        return ResultDTO.success();
    }
}
