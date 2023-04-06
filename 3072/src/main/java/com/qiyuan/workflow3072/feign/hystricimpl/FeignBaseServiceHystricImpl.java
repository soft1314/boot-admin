package com.qiyuan.workflow3072.feign.hystricimpl;

import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.feign.FeignBaseService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Data
public class FeignBaseServiceHystricImpl implements FeignBaseService {
    private Throwable cause;

    @Override
    public ResultDTO passProcess(String businessTable, String businessId) {
        return null;
    }

    @Override
    public ResultDTO destroyProcess(String businessTable, String businessId) {
        return null;
    }

    @Override
    public ResultDTO forceDestroyProcess(String businessTable, String businessId) {
        return null;
    }
}