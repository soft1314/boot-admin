package com.qiyuan.workflow3072.feign.factory;

import com.qiyuan.workflow3072.feign.FeignBaseService;
import com.qiyuan.workflow3072.feign.FeignLogdemoService;
import com.qiyuan.workflow3072.feign.hystricimpl.FeignLogDemoServiceHystricImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

@Service
public class FeignLogDemoServiceHystricImplFactory implements FallbackFactory<FeignLogdemoService> {
    @Override
    public FeignLogdemoService create(Throwable throwable) {
        FeignLogDemoServiceHystricImpl remoteFallback = new FeignLogDemoServiceHystricImpl();
        remoteFallback.setCause(throwable);
        return remoteFallback;
    }
}