package com.qiyuan.workflow3072.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class HisTaskVO {
    private String id;
    private String name;
    private String key;
    private String description;
    private String formKey;
    private String assignee;
    private String processId;
    private String processDefinitionId;
    private String executionId;
    private String businessId;
    private String businessTable;
    private Date createTime;
    private Date endTime;
    private String message;
    private String fullMessage;
    private String processInstanceId;
    private String title;
    private String outcome;
    private String processName;
}
