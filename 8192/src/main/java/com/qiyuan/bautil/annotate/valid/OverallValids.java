package com.qiyuan.bautil.annotate.valid;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD,TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OverallValids {
    OverallValid[] value();
}
