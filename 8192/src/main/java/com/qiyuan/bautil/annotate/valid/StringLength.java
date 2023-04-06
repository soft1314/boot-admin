package com.qiyuan.bautil.annotate.valid;
/**
 * 字符长度校验
 */


import com.qiyuan.bautil.annotate.valid.impl.StringLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {
        METHOD,
        FIELD,
        ANNOTATION_TYPE,
        CONSTRUCTOR,
        PARAMETER
})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {StringLengthValidator.class})
public @interface StringLength {
    int max() default 4000;
    int min() default 0;

//    String regexp() default "";

    String message() default "字符串长度不符合要求。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}