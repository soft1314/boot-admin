package com.qiyuan.bautil.annotate.valid;

import com.qiyuan.bautil.annotate.valid.impl.OverallValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD,TYPE})
@Retention(RUNTIME)
@Repeatable(OverallValids.class)
@Documented
@Constraint(validatedBy = {OverallValidImpl.class})
public @interface OverallValid {
    String value() default "overallValid";
    String message() default "组合校验未通过。";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
