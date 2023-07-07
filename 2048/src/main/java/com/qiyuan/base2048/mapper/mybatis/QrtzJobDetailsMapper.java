package com.qiyuan.base2048.mapper.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.mapper.mybatis.entity.QrtzJobDetails;
import com.qiyuan.base2048.quartz.dto.JobDTO;
import org.apache.ibatis.annotations.Select;

public interface QrtzJobDetailsMapper extends BaseMapper<QrtzJobDetails> {
    @Select("SELECT a.JOB_NAME as jobName,a.JOB_GROUP as jobGroup,a.JOB_CLASS_NAME as jobClassName,b.TRIGGER_NAME as triggerName,b.TRIGGER_GROUP as triggerGroup,b.TRIGGER_STATE as triggerState,c.CRON_EXPRESSION as cronExpression,c.TIME_ZONE_ID as timeZoneId " +
            "FROM qrtz_job_details a JOIN qrtz_triggers b on a.job_name=b.job_name and a.job_group = b.job_group " +
            "JOIN qrtz_cron_triggers c ON a.JOB_NAME = b.JOB_NAME AND b.TRIGGER_NAME = c.TRIGGER_NAME AND b.TRIGGER_GROUP = c.TRIGGER_GROUP")
    <E extends IPage<JobDTO>> E getJobAndTriggerDetails(E page);
}
