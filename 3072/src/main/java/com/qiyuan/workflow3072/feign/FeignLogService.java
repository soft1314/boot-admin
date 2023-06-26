package com.qiyuan.workflow3072.feign;

import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.StringContainerVO;
import com.qiyuan.workflow3072.feign.config.FeignConfig;
import com.qiyuan.workflow3072.feign.hystricimpl.FeignLogServiceHystricImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(contextId = "feignLogService" ,value = "service-rest-log6144", configuration = FeignConfig.class, fallback = FeignLogServiceHystricImpl.class)
public interface FeignLogService {
    @PostMapping(value = "/api/log/free/collector/custom/send")
    ResultDTO sendLog(@RequestBody @Valid StringContainerVO stringContainerVO);
}
