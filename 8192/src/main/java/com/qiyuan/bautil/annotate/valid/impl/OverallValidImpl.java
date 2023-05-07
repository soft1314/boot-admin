package com.qiyuan.bautil.annotate.valid.impl;


import com.qiyuan.bautil.annotate.valid.OverallValid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2023-05-06
 **/
@Slf4j
public class OverallValidImpl implements ConstraintValidator<OverallValid, Object> {
    private String functionName;
    @Override
    public void initialize(OverallValid overallValid) {
        functionName = overallValid.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            //得到方法对象
            Method checkMethod = o.getClass().getMethod(functionName);
            //调用方法，得到返回值
            Object checkRet = checkMethod.invoke(o);
            return Boolean.valueOf(checkRet.toString());
        }catch (Exception ex){
            log.error("综合校验异常。");
            log.error(ex.getMessage(),ex);
        }
        return false;
    }
}
