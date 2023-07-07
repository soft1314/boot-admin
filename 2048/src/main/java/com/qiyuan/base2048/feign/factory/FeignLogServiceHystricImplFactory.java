package com.qiyuan.base2048.feign.factory;

import com.qiyuan.base2048.feign.FeignLogService;
import com.qiyuan.base2048.feign.hystricimpl.FeignLogServiceHystricImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

@Service
public class FeignLogServiceHystricImplFactory implements FallbackFactory<FeignLogService> {
    @Override
    public FeignLogService create(Throwable throwable) {
        FeignLogServiceHystricImpl remoteFallback = new FeignLogServiceHystricImpl();
        remoteFallback.setCause(throwable);
        return remoteFallback;
    }
}