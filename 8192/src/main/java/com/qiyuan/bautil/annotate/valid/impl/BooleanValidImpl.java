package com.qiyuan.bautil.annotate.valid.impl;


import com.qiyuan.bautil.annotate.valid.BooleanValid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-05-6
 **/
@Slf4j
public class BooleanValidImpl implements ConstraintValidator<BooleanValid, Boolean> {
    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext constraintValidatorContext) {
        return value;
    }
    @Override
    public void initialize(BooleanValid constraintAnnotation) {
    }

}


