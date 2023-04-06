package com.qiyuan.base2048.pojo.dto.workflow;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProcessDefinitionDTO {
    /** 流程定义ID  如： Expense:3:1b106482-52b5-11ec-9d10-4c77cb6b51e3 **/
    private String id;

    /** 对应XML文件中的name，例："ExpenseProcess" **/
    private String name;

    /** 对应XML文件中的ID，例：“Expense” **/
    private String key;

    /** 对应XML文件中的documentation，例 “报销流程” **/
    private String description;

    /** 发布的版本，每发布一次加1   **/
    private BigDecimal version;

    /** 租户ID **/
    private String tenantId;

    /** 部署ID    1aa1eb3c-52b5-11ec-9d10-4c77cb6b51e3 **/
    private String deploymentId;

    /** 部署时间 **/
    private Date deploymentTime;

    /** 发布名称 **/
    private String deploymentName;

    /** 发布类别 **/
    private String deploymentCategory;

    /** 发布KEY **/
    private String deploymentKey;
}

