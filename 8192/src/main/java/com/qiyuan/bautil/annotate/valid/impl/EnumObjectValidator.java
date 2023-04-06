package com.qiyuan.bautil.annotate.valid.impl;


import com.qiyuan.bautil.annotate.valid.EnumObjectValid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021/5/22 9:26
 **/
@Slf4j
public class EnumObjectValidator implements ConstraintValidator<EnumObjectValid, Object> {
    /** 枚举校验注解 */
    private EnumObjectValid annotation;

    @Override
    public void initialize(EnumObjectValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        Class<?> cls = annotation.target();
        boolean ignoreEmpty = annotation.ignoreEmpty();
        // target为枚举，并且value有值，或者不忽视空值，才进行校验
        if (cls.isEnum() && value != null) {
            Object[] objects = cls.getEnumConstants();
            try {
//                Method method = cls.getMethod("getValue");
                Method method = cls.getMethod(annotation.method());
                for (Object obj : objects) {
                    Object code = method.invoke(obj);
                    if (value.equals( code)) {
                        result = true;
                        break;
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.warn("EnumObjectValidator call isValid() method exception.");
                result = false;
            }
        } else {
            result = true;
        }
        return result;
    }
}
