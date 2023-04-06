package com.qiyuan.bautil.annotate.valid.impl;


import com.qiyuan.bautil.annotate.valid.SelfCheck4Valid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-03 10:32
 **/
@Slf4j
public class SelfCheck4ValidImpl implements ConstraintValidator<SelfCheck4Valid, Object> {
    @Override
    public void initialize(SelfCheck4Valid selfCheck4Valid) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            //得到方法对象
            Method checkMethod = o.getClass().getMethod("selfCheck4");
            //调用方法，得到返回值
            Object checkRet = checkMethod.invoke(o);
            return Boolean.valueOf(checkRet.toString());
        }catch (Exception ex){
            log.error("自检4异常。");
            log.error(ex.getMessage(),ex);
        }
        return false;
    }
}
