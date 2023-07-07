package com.qiyuan.base2048.quartz.job.jobs;

import com.qiyuan.base2048.quartz.job.BaseJobTemplate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;

@Slf4j
@Component
@DisallowConcurrentExecution
public class TestJob extends BaseJobTemplate {
    @Override
    public void runing(JobExecutionContext jobExecutionContext)  {
        try {
            log.info("测试任务开始：【{}】", Instant.now().atOffset(ZoneOffset.ofHours(8)));
            System.out.println("============= 测试任务正在运行 =====================");
            Thread.sleep(5000);
            System.out.println("============= Test job is running ===============");
            log.info("测试任务结束：【{}】", Instant.now().atOffset(ZoneOffset.ofHours(8)));
        } catch (Exception ex) {
            log.error("测试任务异常：【{}】", Instant.now().atOffset(ZoneOffset.ofHours(8)));
            log.error(ex.getMessage(), ex);
        }
    }
}
