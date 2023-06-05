package com.qiyuan.workflow3072.feign;

import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.feign.config.FeignConfig;
import com.qiyuan.workflow3072.feign.factory.FeignBaseServiceHystricImplFactory;
import com.qiyuan.workflow3072.feign.factory.FeignLogDemoServiceHystricImplFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "feignLog6144Service" ,value = "service-rest-log6144", configuration = FeignConfig.class, fallbackFactory = FeignLogDemoServiceHystricImplFactory.class)
public interface FeignLogdemoService {
    @GetMapping(value = "/api/logdemo/free/test1")
    String freeTest1(@RequestParam("msg") String msg);

    @GetMapping(value = "/api/logdemo/auth/test1")
    String authTest1(@RequestParam("msg") String msg);
}
