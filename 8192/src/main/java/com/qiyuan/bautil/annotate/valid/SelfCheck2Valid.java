package com.qiyuan.bautil.annotate.valid;




import com.qiyuan.bautil.annotate.valid.impl.SelfCheck2ValidImpl;

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
@Constraint(validatedBy = {SelfCheck2ValidImpl.class})
public @interface SelfCheck2Valid {
    String message() default "自检2未通过。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
