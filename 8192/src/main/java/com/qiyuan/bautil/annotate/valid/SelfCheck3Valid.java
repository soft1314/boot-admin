package com.qiyuan.bautil.annotate.valid;



import com.qiyuan.bautil.annotate.valid.impl.SelfCheck3ValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD,TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {SelfCheck3ValidImpl.class})
public @interface SelfCheck3Valid {
    String message() default "自检3未通过。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
