package com.qiyuan.bautil.constant;

/**
 * 流程变量的KEY
 */
public interface ConstantWorkflowVarKey {
    /** 流程、任务标题 **/
    String TITLE="title";
    /** 业务表名 **/
    String BUSINESS_TABLE="businessTable";
    /** 业务表主键 **/
    String BUSINESS_ID="businessId";
    /** 流程启动者 **/
    String INITIATOR="initiator";
    /** 流程业务数据所属行政区划编码 **/
    String ADM_DIV_CODE="admDivCode";
    /** 流程业务数据所属单位编码 **/
    String ORG_CODE="orgCode";
    /** 金额，一般用于流程分支的度量 **/
    String MONEY="money";
    /** 天数，一般用于流程分支的度量 **/
    String DAYS = "days";
    /** 完成任务的结果，用于流程走向，包括：通过、驳回、撤回、退回起点、销毁 **/
    String OUTCOME="outcome";
    /** 命令为”通过“时，将处理为写入变量，用于撤回任务时知道上一岗位是谁 **/
    String LAST_ASSIGNEE="lastAssignee";
}
