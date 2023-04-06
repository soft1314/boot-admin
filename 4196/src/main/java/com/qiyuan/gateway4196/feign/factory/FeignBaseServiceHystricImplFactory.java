package com.qiyuan.gateway4196.feign.factory;

import com.qiyuan.gateway4196.feign.FeignBaseService;
import com.qiyuan.gateway4196.feign.hystricImpl.FeignBaseServiceHystricImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-26 15:55
 **/
@Service
public class FeignBaseServiceHystricImplFactory implements FallbackFactory<FeignBaseService> {
    @Override
    public FeignBaseService create(Throwable throwable) {
        FeignBaseServiceHystricImpl remoteFallback = new FeignBaseServiceHystricImpl();
        remoteFallback.setCause(throwable);
        return remoteFallback;
    }
}
