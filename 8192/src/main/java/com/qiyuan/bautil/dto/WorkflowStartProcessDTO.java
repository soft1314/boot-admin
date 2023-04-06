package com.qiyuan.bautil.dto;

import lombok.Data;

import java.util.Map;
/**
 * 启动流程DTO对象
 */
@Data
public class WorkflowStartProcessDTO {
    /* 流程定义KEY */
    private String procDefKey;
    /* 业务表表名 */
    private String businessTable;
    /* 业务表编号,主键 */
    private String businessId;
    /* 流程标题，显示在待办任务标题 */
    private String title;
    /* 流程变量 */
    private Map<String, Object> vars;
}
