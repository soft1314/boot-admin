package com.qiyuan.bautil.annotate.response.pojo;

import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-26 11:10
 **/
@Data
public class ResultException extends Exception {
    private ResultStatus resultStatus;
}
