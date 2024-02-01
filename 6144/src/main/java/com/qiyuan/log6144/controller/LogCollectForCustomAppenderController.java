package com.qiyuan.log6144.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qiyuan.bautil.dto.LogDTO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.StringContainerVO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.log6144.mapper.json.ObjectMapperFactory;
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

        ObjectMapper mapper = ObjectMapperFactory.createNonStrictObjectMapper();

        LogDTO logDTO = mapper.readValue(stringContainerVO.getStrValue(), LogDTO.class);
        String level = logDTO.getLevel();
        if("TRACE".equals(level)){
            log.trace(logDTO.getMessage());
            log.trace(logDTO.toString());
        }else if("DEBUG".equals(level)){
            log.debug(logDTO.getMessage());
            log.debug(logDTO.toString());
        }else if("INFO".equals(level)){
            log.info(logDTO.getMessage());
            log.info(logDTO.toString());
        }else if("WARN".equals(level)){
            log.warn(logDTO.getMessage());
            log.warn(logDTO.toString());
        }else if("ERROR".equals(level)) {
            log.error(logDTO.getMessage());
            log.error(logDTO.toString());
        }else if("FATAL".equals(level)){
            log.error(logDTO.getMessage());
            log.error(logDTO.toString());
        }else{
            log.error(logDTO.getMessage());
            log.error(logDTO.toString());
        }
        //暂时不入DB
//        logSaver.saveLog(logDTO);
        return ResultDTO.success();
    }
}
