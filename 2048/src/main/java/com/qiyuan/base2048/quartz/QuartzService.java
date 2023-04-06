package com.qiyuan.base2048.quartz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.quartz.dto.JobDTO;
import com.qiyuan.base2048.quartz.job.BaseJob;
import org.quartz.SchedulerException;

public interface QuartzService {
    IPage<JobDTO> queryJob(int pageNum, int pageSize) throws Exception;
    void addJob(BaseJob job) throws SchedulerException;
    void pauseJob(String jobName,String jobGroupName) throws SchedulerException;
    void resumeJob(String jobName,String jobGroupName) throws SchedulerException;
    boolean deleteJob(String jobName,String jobGroupName) throws SchedulerException;
}
