package com.qiyuan.bautil.annotate.response.pojo;

import com.qiyuan.bautil.annotate.response.enumer.ResponseStatus;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-25 22:25
 **/
@Data
@ToString
public class ControllerResultDTO<T> {
    /**
    时间戳	timestamp	时间		是
    状态码	status	字符串	http状态码   扩展业务码   200时携带业务数据	是
    错误类别	error	字符串		否
    异常描述	exception	字符串		否
    消息	message	字符串		否
    资源定位	path	字符串		是
    携带数据	data	字符串	json	否 ----todo 可加密
     */

    private Date timestamp;
    private Integer status;
    private String error;
    private String exception;
    private String message;
    private String path;
    private ResultDTO data;
    private String tokenTimeout;

    /** 业务成功返回业务代码和描述信息 */
    public static ControllerResultDTO success(String path,String tokenTimeout) {
        ControllerResultDTO controllerResultDTO = new ControllerResultDTO();
        controllerResultDTO.setTimestamp(new Date());
        controllerResultDTO.setPath(path);
        controllerResultDTO.setMessage(ResponseStatus.SUCCESS.getMessage());
        controllerResultDTO.setException("");
        controllerResultDTO.setError("");
        controllerResultDTO.setStatus(ResponseStatus.SUCCESS.getCode());
        controllerResultDTO.setData(null);
        controllerResultDTO.setTokenTimeout(tokenTimeout);
        return controllerResultDTO;
    }
    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> ControllerResultDTO success(String path, T data,String tokenTimeout) {
        ControllerResultDTO controllerResultDTO = new ControllerResultDTO();
        controllerResultDTO.setTimestamp(new Date());
        controllerResultDTO.setPath(path);
        controllerResultDTO.setMessage(ResponseStatus.SUCCESS.getMessage());
        controllerResultDTO.setException("");
        controllerResultDTO.setError("");
        controllerResultDTO.setStatus(ResponseStatus.SUCCESS.getCode());
        if(data instanceof ResultDTO) {
            controllerResultDTO.setData((ResultDTO)data);
        }else{
            ResultDTO dataInControllerResultDTO = ResultDTO.success(data);
            controllerResultDTO.setData(dataInControllerResultDTO);
        }
        controllerResultDTO.setTokenTimeout(tokenTimeout);
        return controllerResultDTO;
    }
    /** 业务异常返回业务代码和描述信息 */
    public static <T> ControllerResultDTO failure(String path, ResponseException ex,String tokenTimeout) {
        ControllerResultDTO controllerResultDTO = new ControllerResultDTO();
        controllerResultDTO.setTimestamp(new Date());
        controllerResultDTO.setPath(path);
        controllerResultDTO.setMessage(ex.getResponseStatus().getMessage());
        controllerResultDTO.setException(ex.getMessage());
        controllerResultDTO.setError(ex.getResponseStatus().getMessage());
        controllerResultDTO.setStatus(ex.getResponseStatus().getCode());
        controllerResultDTO.setData(null);
        controllerResultDTO.setTokenTimeout(tokenTimeout);
        return controllerResultDTO;
    }
    public static <T> ControllerResultDTO failure(String path, Exception ex,String tokenTimeout) {
        ControllerResultDTO controllerResultDTO = new ControllerResultDTO();
        controllerResultDTO.setTimestamp(new Date());
        controllerResultDTO.setPath(path);
        controllerResultDTO.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        controllerResultDTO.setException(ex.getMessage());
        controllerResultDTO.setError(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        controllerResultDTO.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getCode());
        controllerResultDTO.setData(null);
        controllerResultDTO.setTokenTimeout(tokenTimeout);
        return controllerResultDTO;
    }
    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> ControllerResultDTO failure(String path,String tokenTimeout) {
        ControllerResultDTO controllerResultDTO = new ControllerResultDTO();
        controllerResultDTO.setTimestamp(new Date());
        controllerResultDTO.setPath(path);
        controllerResultDTO.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        controllerResultDTO.setException(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        controllerResultDTO.setError(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        controllerResultDTO.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getCode());
        controllerResultDTO.setData(null);
        controllerResultDTO.setTokenTimeout(tokenTimeout);
        return controllerResultDTO;
    }

    public boolean isSuccess(){
        return ResponseStatus.SUCCESS.getCode().compareTo(this.status) == 0;
    }
    public boolean isFailure(){
        return !this.isSuccess();
    }
}
