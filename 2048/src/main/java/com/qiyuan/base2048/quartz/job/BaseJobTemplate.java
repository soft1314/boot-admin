package com.qiyuan.base2048.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public abstract class BaseJobTemplate extends BaseJob {

    @Value("${base2048.job.enable:false}")
    private Boolean JOB_LOCAL_RUNING;

    @Override
    public final void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (JOB_LOCAL_RUNING) {
            try {
                this.runing(jobExecutionContext);
            } catch (Exception ex) {
                throw new JobExecutionException(ex);
            }
        } else {
            log.info("配置参数不允许在本机执行定时任务");
        }
    }

    public abstract void runing(JobExecutionContext jobExecutionContext);
}

