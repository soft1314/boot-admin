package com.qiyuan.bautil.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class SysOperLog {
    /** 日志主键 */
//    private Long operId;

    /** 操作模块 */
    private String businessModule;

    /** 业务类型 */
    private String businessType;

    /** 业务类型数组 */
//    private Integer[] businessTypes;

    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作类别 */
    private String operatorType;

    /** 操作人员 */
    private String operName;

    /** 部门名称 */
//    private String deptName;

    /** 请求url */
    private String operUrl;

    /** 操作地址 */
    private String operIp;

    /** 请求参数 */
    private String operParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
//    private Integer status;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** 消耗时间 */
    private Long costTime;
}
