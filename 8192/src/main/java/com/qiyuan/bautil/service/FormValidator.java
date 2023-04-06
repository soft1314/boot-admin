package com.qiyuan.bautil.service;


import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.validation.BindingResult;

public interface FormValidator {
    ResultDTO generateMessage(BindingResult bindingResult) throws Exception;
}
