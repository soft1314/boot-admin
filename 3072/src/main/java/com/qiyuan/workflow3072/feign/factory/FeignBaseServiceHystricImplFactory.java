package com.qiyuan.workflow3072.feign.factory;

import com.qiyuan.workflow3072.feign.FeignBaseService;
import com.qiyuan.workflow3072.feign.hystricimpl.FeignBaseServiceHystricImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

@Service
public class FeignBaseServiceHystricImplFactory implements FallbackFactory<FeignBaseService> {
    @Override
    public FeignBaseService create(Throwable throwable) {
        FeignBaseServiceHystricImpl remoteFallback = new FeignBaseServiceHystricImpl();
        remoteFallback.setCause(throwable);
        return remoteFallback;
    }
}