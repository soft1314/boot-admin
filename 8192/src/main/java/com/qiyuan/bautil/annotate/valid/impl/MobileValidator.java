package com.qiyuan.bautil.annotate.valid.impl;



import com.qiyuan.bautil.annotate.valid.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-04-28 10:51
 **/
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    /**
     * 手机号的正则表达式.
     */
    private static Pattern pattern = Pattern.compile(
            "^0?(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Matcher m = pattern.matcher(value);
        return m.matches();
    }

    @Override
    public void initialize(Mobile constraintAnnotation) {}
}

