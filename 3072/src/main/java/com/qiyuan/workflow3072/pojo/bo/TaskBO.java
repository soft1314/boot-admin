package com.qiyuan.workflow3072.pojo.bo;

import lombok.Data;

import java.util.Date;

@Data
public class TaskBO {
    private String assignee;
    private Date startTime;
    private Date endTime;
    private String notes;
    private String name;
}
