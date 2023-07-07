package com.qiyuan.base2048.quartz.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.QrtzJobDetailsMapper;
import com.qiyuan.base2048.quartz.QuartzService;
import com.qiyuan.base2048.quartz.dto.JobDTO;
import com.qiyuan.base2048.quartz.job.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {
    /**
     * Scheduler代表一个调度容器,一个调度容器可以注册多个JobDetail和Trigger.当Trigger和JobDetail组合,就可以被Scheduler容器调度了
     */
    @Autowired
    private Scheduler scheduler;
    @Resource
    private QrtzJobDetailsMapper qrtzJobDetailsMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @Autowired
    public QuartzServiceImpl(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    @Override
    public IPage<JobDTO> queryJob(int pageNum, int pageSize) throws Exception{
//        IPage<JobDTO> jobDTOPage = new Page<>(pageNum,pageSize);
//        jobDTOPage = qrtzJobDetailsMapper.getJobAndTriggerDetails(jobDTOPage);
//        return jobDTOPage;
        List<JobDTO> jobList = null;
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    JobDTO jobDetails = new JobDTO();
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        jobDetails.setCronExpression(cronTrigger.getCronExpression());
                        jobDetails.setTimeZoneId(cronTrigger.getTimeZone().getDisplayName());
                    }
                    jobDetails.setTriggerGroup(trigger.getKey().getName());
                    jobDetails.setTriggerName(trigger.getKey().getGroup());
                    jobDetails.setJobGroup(jobKey.getGroup());
                    jobDetails.setJobName(jobKey.getName());
                    jobDetails.setStartTime(trigger.getStartTime());
                    jobDetails.setJobClassName(scheduler.getJobDetail(jobKey).getJobClass().getName());
                    jobDetails.setNextFireTime(trigger.getNextFireTime());
                    jobDetails.setPreviousFireTime(trigger.getPreviousFireTime());
                    jobDetails.setTriggerState(scheduler.getTriggerState(trigger.getKey()).name());
                    jobList.add(jobDetails);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        IPage<JobDTO> jobDTOPage = new Page<>(pageNum,pageSize);
        jobDTOPage.setRecords(jobList);
        jobDTOPage.setTotal(jobList.size());
        jobDTOPage.setCurrent(1);
        jobDTOPage.setPages(1);
        jobDTOPage.setSize(jobList.size());
        return jobDTOPage;
    }

    /**
     * 添加一个任务
     * @param job
     * @throws SchedulerException
     */
    @Override
    public void addJob(BaseJob job) throws SchedulerException {

        /** 创建JobDetail实例,绑定Job实现类
         * JobDetail 表示一个具体的可执行的调度程序,job是这个可执行调度程序所要执行的内容
         * 另外JobDetail还包含了这个任务调度的方案和策略**/
        // 指明job的名称，所在组的名称，以及绑定job类
        JobDetail jobDetail = JobBuilder.newJob(job.getBeanClass())
                .withIdentity(job.getJobKey())
                .withDescription(job.getDescription())
                .usingJobData(job.getDataMap())
                .build();

        /**
         * Trigger代表一个调度参数的配置,什么时候去调度
         */
        //定义调度触发规则, 使用cronTrigger规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(),job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .startNow()
                .build();
        //将任务和触发器注册到任务调度中去
        scheduler.scheduleJob(jobDetail,trigger);
        //判断调度器是否启动
        if(!scheduler.isStarted()){
            scheduler.start();
        }
        log.info(String.format("定时任务:%s.%s-已添加到调度器!", job.getJobGroup(),job.getJobName()));
    }

    /**
     * 根据任务名和任务组名来暂停一个任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void pauseJob(String jobName,String jobGroupName) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName,jobGroupName));
    }

    /**
     * 根据任务名和任务组名来恢复一个任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(String jobName,String jobGroupName) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName,jobGroupName));
    }

    public void rescheduleJob(String jobName,String jobGroupName,String cronExpression,String description) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(description).withSchedule(scheduleBuilder).build();

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 根据任务名和任务组名来删除一个任务
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public boolean deleteJob(String jobName,String jobGroupName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,jobGroupName);
        scheduler.pauseTrigger(triggerKey); //先暂停
        scheduler.unscheduleJob(triggerKey); //取消调度
        boolean flag = scheduler.deleteJob(JobKey.jobKey(jobName,jobGroupName)); //删除任务
        /**
         * 注：这里返回false，可是成功删除了
         */
        return flag;
    }

    private JobDTO createJob(String jobName, String jobGroup, Scheduler scheduler, Trigger trigger)
            throws SchedulerException {
        JobDTO job = new JobDTO();
        job.setJobName(jobName);
        job.setJobGroup(jobGroup);
        job.setDescription("触发器:" + trigger.getKey());
//        job.setNextTime(trigger.getNextFireTime());
//        job.setPreviousTime(trigger.getPreviousFireTime());

        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        job.setTriggerState(triggerState.name());

        if(trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger)trigger;
            String cronExpression = cronTrigger.getCronExpression();
            job.setCronExpression(cronExpression);
        }
        return job;
    }

}

