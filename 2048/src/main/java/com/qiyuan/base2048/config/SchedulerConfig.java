package com.qiyuan.base2048.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    @Autowired
    private MyJobFactory myJobFactory;

    @Value("${base2048.job.enable:false}")
    private Boolean JOB_LOCAL_RUNING;
    @Value("${base2048.job.datasource.driver-class-name}")
    private String dsDriver;
    @Value("${base2048.job.datasource.url}")
    private String dsUrl;
    @Value("${base2048.job.datasource.username}")
    private String dsUser;
    @Value("${base2048.job.datasource.password}")
    private String dsPassword;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 延时启动
        factory.setStartupDelay(20);
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
//        factory.setOverwriteExistingJobs(true);
        // 加载quartz数据源配置
        factory.setQuartzProperties(quartzProperties());
        // 自定义Job Factory，用于Spring注入
        factory.setJobFactory(myJobFactory);

        //原因 : qrtz 在集群模式下 存在 同一个任务 一个在A服务器任务被分配出去 另一个B服务器任务不再分配的情况.
        //
        if(!JOB_LOCAL_RUNING){
            // 设置调度器自动运行
            factory.setAutoStartup(false);
        }
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        Properties properties = propertiesFactoryBean.getObject();
        properties.setProperty("org.quartz.dataSource.qzDS.driver",dsDriver);
        properties.setProperty("org.quartz.dataSource.qzDS.URL",dsUrl);
        properties.setProperty("org.quartz.dataSource.qzDS.user",dsUser);
        properties.setProperty("org.quartz.dataSource.qzDS.password",dsPassword);
        return properties;
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="scheduler")
    public Scheduler scheduler() throws Exception {
        return schedulerFactoryBean().getScheduler();
    }
}

