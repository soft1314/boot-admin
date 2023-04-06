package com.qiyuan.bautil.service.impl;


import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.ValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-18 16:04
 **/
@Service
@Slf4j
public class FormValidatorImpl implements FormValidator {
    @Override
    public ResultDTO generateMessage(BindingResult bindingResult) throws Exception {
        String msg = this.getMessage(bindingResult);
        return ResultDTO.failureCustom(msg);
    }
    /**
     * 生成返回串
     * @param bindingResult
     * @return
     */
    private String getMessage(BindingResult bindingResult){
        log.info(bindingResult.toString());
        List<ObjectError> objectErrorList=bindingResult.getAllErrors();
        String msg= ValidUtil.getFormValidErrsMsgNoBr(objectErrorList);
        log.info(msg);
        return msg;
    }
}

