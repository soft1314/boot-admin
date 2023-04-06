package com.qiyuan.bautil.annotate.response.enumer;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-07-22 10:52
 **/
@ToString
@Getter
public enum ResultStatus {
    /** 100接口调用成功并成功返回 */
    SUCCESS(HttpStatus.OK,100, "接口调用成功并成功返回"),
    /** 101参数错误------校验 */
    BAD_PARAMETER(HttpStatus.BAD_REQUEST,101, "参数校验错误"),
    /** 102服务器内部错误----异常 */
    INTERNAL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,102, "服务器内部异常"),
    /** 103方法内部错误-----逻辑 */
    FUNCTION_LOGIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,103, "方法内部错误"),
    /** 104 生成文件时，文件已存在 **/
    FUNCTION_ALREADY_EXIST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,104, "文件已存在"),
    /** 998 鉴权失败----逻辑 */
    FUNCTION_AUTHENTICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,998, "鉴权失败"),
    /** 999 其他错误-----逻辑 */
    FUNCTION_CUSTOM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,999, "自定义错误");
    /** 返回的HTTP状态码,  符合http请求 */
    private HttpStatus httpStatus;
    /** 业务异常码 */
    private Integer code;
    /** 业务异常信息描述 */
    private String message;

    ResultStatus(HttpStatus httpStatus, Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
