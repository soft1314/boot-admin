package com.qiyuan.bautil.annotate.response;
/**
 * 对RestController的返回进行统一封装
 */

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseBodyInController {

}
