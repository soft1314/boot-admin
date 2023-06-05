package com.qiyuan.workflow3072.feign.hystricimpl;

import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.feign.FeignBaseService;
import com.qiyuan.workflow3072.feign.FeignLogdemoService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Data
public class FeignLogDemoServiceHystricImpl implements FeignLogdemoService {
    private Throwable cause;

    @Override
    public String freeTest1(String msg) {
        return null;
    }

    @Override
    public String authTest1(String msg) {
        return null;
    }
}