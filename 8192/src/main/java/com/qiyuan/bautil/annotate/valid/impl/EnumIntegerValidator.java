package com.qiyuan.bautil.annotate.valid.impl;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2019-10-28 21:41
 **/


import com.qiyuan.bautil.annotate.valid.EnumIntegerValid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 枚举参数校验处理类 * *
 */
@Slf4j
public class EnumIntegerValidator implements ConstraintValidator<EnumIntegerValid, Integer> {
    /** 枚举校验注解 */
    private EnumIntegerValid annotation;

    @Override
    public void initialize(EnumIntegerValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        Class<?> cls = annotation.target();
        boolean ignoreEmpty = annotation.ignoreEmpty();
        // target为枚举，并且value有值，或者不忽视空值，才进行校验
        if (cls.isEnum() && value != null) {
            Object[] objects = cls.getEnumConstants();
            try {
                Method method = cls.getMethod("getValue");
                for (Object obj : objects) {
                    Object code = method.invoke(obj);
                    if (value.compareTo((Integer) code) == 0) {
                        result = true;
                        break;
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.warn("EnumValidator call isValid() method exception.");
                result = false;
            }
        } else {
            result = true;
        }
        return result;
    }
}


