package com.qiyuan.workflow3072.pojo.vo;


import java.util.Date;

import lombok.Data;
import org.flowable.task.api.Task;

@Data
public class TaskVO {
    public TaskVO(Task task){
        this.setId(task.getId());
        this.setKey(task.getTaskDefinitionKey());
        this.setName(task.getName());
        this.setDescription(task.getDescription());
        this.setAssignee(task.getAssignee());
        this.setFormKey(task.getFormKey());
        this.setProcessInstanceId(task.getProcessInstanceId());
        this.setProcessDefinitionId(task.getProcessDefinitionId());
        this.setExecutionId(task.getExecutionId());
        this.setCreateTime(task.getCreateTime());
    }
    private  String id;
    private String name;
    private String key;
    private String description;
    private  String formKey;
    private  String assignee;
    private String processInstanceId;
    private String processDefinitionId;
    private String executionId;
    private Date createTime;

    private String businessId;
    private String businessTable;
    private String title;
    private String processName;
}
