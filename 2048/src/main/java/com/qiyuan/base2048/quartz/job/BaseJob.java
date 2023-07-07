package com.qiyuan.base2048.quartz.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;

import java.io.Serializable;

public abstract class BaseJob implements Job, Serializable {
    private static final String JOB_MAP_KEY = "self";

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";

    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private Class beanClass = this.getClass();
    /**
     * 任务是否有状态
     */
    private String isConcurrent;

    /**
     * Spring bean
     */
    private String springBean;

    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 为了将执行后的任务持久化到数据库中
     */
    @JsonIgnore
    private JobDataMap dataMap = new JobDataMap();

    public JobKey getJobKey(){
        return JobKey.jobKey(jobName, jobGroup);// 任务名称和组构成任务key
    }

    public JobDataMap getDataMap(){
        if(dataMap.size() == 0){
            dataMap.put(JOB_MAP_KEY,this);
        }
        return dataMap;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getSpringBean() {
        return springBean;
    }

    public void setSpringBean(String springBean) {
        this.springBean = springBean;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}

