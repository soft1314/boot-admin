package com.qiyuan.gateway4096.feign;


import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.gateway4096.feign.config.FeignConfig;
import com.qiyuan.gateway4096.feign.factory.FeignBaseServiceHystricImplFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
//@Component
@FeignClient(contextId = "feignBaseService" ,value = "base2048", configuration = FeignConfig.class, fallbackFactory = FeignBaseServiceHystricImplFactory.class)
//@FeignClient(contextId = "feignBaseService" ,value = "service-rest-base2048")
public interface FeignBaseService {
    @GetMapping(value = "/api/system/free/resource/role/list")
    List<String> fetchRoleListByUri(@RequestParam("uri") String uri) throws Exception;

    @GetMapping(value = "/api/system/free/resource/menudto")
    MenuDTO fetchMenuDtoByUri(@RequestParam("uri") String uri);
}
