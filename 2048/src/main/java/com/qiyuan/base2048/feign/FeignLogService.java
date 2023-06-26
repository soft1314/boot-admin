package com.qiyuan.base2048.feign;

import com.qiyuan.base2048.feign.config.FeignConfig;
import com.qiyuan.base2048.feign.hystricimpl.FeignLogServiceHystricImpl;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.StringContainerVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

//@FeignClient(contextId = "feignLogService" ,value = "service-rest-log6144", configuration = FeignConfig.class, fallbackFactory = FeignLogServiceHystricImplFactory.class)
@FeignClient(contextId = "feignLogService" ,value = "service-rest-log6144", configuration = FeignConfig.class, fallback = FeignLogServiceHystricImpl.class)
public interface FeignLogService {
    /** 发送日志串 **/
    @PostMapping(value = "/api/log/free/collector/custom/send")
    ResultDTO sendLog(@RequestBody @Valid StringContainerVO stringContainerVO);
}