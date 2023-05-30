package com.qiyuan.bautil.annotate.valid.impl;


import com.qiyuan.bautil.annotate.valid.StringLength;
import com.qiyuan.bautil.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-04-28 10:53
 **/
@Slf4j
public class StringLengthValidator implements ConstraintValidator<StringLength, String> {
    private int max;
    private int min;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if(StringUtil.isBlank(value)){
                if(min > 0){
                    return false;
                }else {
                    return true;
                }
            }
            byte[] tmpbyte = value.getBytes("UTF-8");
            int length = tmpbyte.length;
            if(length < min || length > max){
                return false;
            }
            return true;
        }catch (Exception ex){
            log.error("注解校验StringLength发生异常啦。");
            log.error(ex.getMessage(),ex);
            return false;
        }

    }
    /**
     * 初始化方法，在里面进行一些设定，我在这里获取注解中属性值，方法参数，就是上面泛型参数的第一个参数 ；
     */
    @Override
    public void initialize(StringLength constraintAnnotation) {
        max = constraintAnnotation.max();
        min = constraintAnnotation.min();
    }

}


