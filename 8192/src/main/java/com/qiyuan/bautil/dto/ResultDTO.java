package com.qiyuan.bautil.dto;

import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import lombok.Data;
import lombok.ToString;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-25 10:27
 **/
@Data
@ToString
public class ResultDTO<T> {
    /** 业务错误码 */
    private Integer code;
    /** 信息描述 */
    private String message;
    /** 返回参数 */
    private T data;
    private ResultDTO() {
    }
    private ResultDTO(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    /** 业务成功返回业务代码和描述信息 */
    public static ResultDTO<Void> success() {
        return new ResultDTO<Void>(ResultStatus.SUCCESS, null);
    }

    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<T>(ResultStatus.SUCCESS, data);
    }

    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> ResultDTO<T> success(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        return new ResultDTO<T>(resultStatus, data);
    }

    /** 业务异常返回业务代码和描述信息 */
    public static <T> ResultDTO<T> failure() {
        return new ResultDTO<T>(ResultStatus.FUNCTION_LOGIC_ERROR, null);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> ResultDTO<T> failure(ResultStatus resultStatus) {
        return failure(resultStatus, null);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> ResultDTO<T> failure(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return new ResultDTO<T>(ResultStatus.FUNCTION_LOGIC_ERROR, null);
        }
        return new ResultDTO<T>(resultStatus, data);
    }

    /** 业务异常返回自定义业务代码和描述信息 */
    public static <T> ResultDTO<T> failureCustom(String message) {
        ResultDTO<T> resultDTO = new ResultDTO<T>();
        resultDTO.setCode(ResultStatus.FUNCTION_CUSTOM_ERROR.getCode());
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static <T> ResultDTO<T> successCustom(String message, T data) {
        ResultDTO<T> resultDTO = new ResultDTO<T>(ResultStatus.SUCCESS, data);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public boolean isSuccess(){
        return ResultStatus.SUCCESS.getCode().equals(this.code);
    }
    public boolean isFailure(){
        return !this.isSuccess();
    }
}