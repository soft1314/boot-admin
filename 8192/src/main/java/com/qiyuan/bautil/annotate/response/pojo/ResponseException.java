package com.qiyuan.bautil.annotate.response.pojo;

import com.qiyuan.bautil.annotate.response.enumer.ResponseStatus;
import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-25 11:30
 **/
@Data
public class ResponseException extends Exception {
    private ResponseStatus responseStatus;
}
