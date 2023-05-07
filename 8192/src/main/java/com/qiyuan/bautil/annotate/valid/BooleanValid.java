package com.qiyuan.bautil.annotate.valid;
/**
 * 字符长度校验
 */


import com.qiyuan.bautil.annotate.valid.impl.BooleanValidImpl;

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
@Constraint(validatedBy = {BooleanValidImpl.class})
public @interface BooleanValid {
    boolean value() default true;

    String message() default "综合校验未通过。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}