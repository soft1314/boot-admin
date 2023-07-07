package com.qiyuan.base2048.quartz.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JobDTO {
    private String jobClassName;
    private String jobName;
    private String jobGroup;
    private String description;
    private String cronExpression;
    private String triggerName;
    private String triggerGroup;
    private String timeZoneId;
    private String triggerState;
    private Date startTime;
    private Date nextFireTime;
    private Date previousFireTime;
}
