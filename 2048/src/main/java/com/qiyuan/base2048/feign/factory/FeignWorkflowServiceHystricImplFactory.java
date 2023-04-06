package com.qiyuan.base2048.feign.factory;

import com.qiyuan.base2048.feign.FeignWorkflowService;
import com.qiyuan.base2048.feign.hystricimpl.FeignWorkflowServiceHystricImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

@Service
public class FeignWorkflowServiceHystricImplFactory implements FallbackFactory<FeignWorkflowService> {
    @Override
    public FeignWorkflowService create(Throwable throwable) {
        FeignWorkflowServiceHystricImpl remoteFallback = new FeignWorkflowServiceHystricImpl();
        remoteFallback.setCause(throwable);
        return remoteFallback;
    }
}