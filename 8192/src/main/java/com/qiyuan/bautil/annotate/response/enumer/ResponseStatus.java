package com.qiyuan.bautil.annotate.response.enumer;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-25 10:26
 **/
@ToString
@Getter
public enum ResponseStatus {
    /** 成功 */
    SUCCESS(HttpStatus.OK, 200, "OK"),
    /** 错误的请求 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    /** 错误的请求 */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "Unauthorized"),
    /** 内部服务错误 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),;

    /** 返回的HTTP状态码,  符合http请求 */
    private HttpStatus httpStatus;
    /** 业务异常码 */
    private Integer code;
    /** 业务异常信息描述 */
    private String message;

    ResponseStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
