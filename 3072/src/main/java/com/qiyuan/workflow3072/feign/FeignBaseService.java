package com.qiyuan.workflow3072.feign;

import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.feign.config.FeignConfig;
import com.qiyuan.workflow3072.feign.factory.FeignBaseServiceHystricImplFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "feignBaseService" ,value = "service-rest-base2048", configuration = FeignConfig.class, fallbackFactory = FeignBaseServiceHystricImplFactory.class)
public interface FeignBaseService {
    /** 将流程业务数据置为已通过流程 **/
    @GetMapping(value = "/api/system/auth/workflow/finish/pass")
    ResultDTO passProcess(@RequestParam("businessTable") String businessTable, @RequestParam("businessId") String businessId);

    /** 将流程业务数据置为已正常销毁 **/
    @GetMapping(value = "/api/system/auth/workflow/finish/destroy")
    ResultDTO destroyProcess(@RequestParam("businessTable") String businessTable, @RequestParam("businessId") String businessId);

    /** 将流程业务数据置为已强制销毁 **/
    @GetMapping(value = "/api/system/auth/workflow/finish/force")
    ResultDTO forceDestroyProcess(@RequestParam("businessTable") String businessTable, @RequestParam("businessId") String businessId);

}
