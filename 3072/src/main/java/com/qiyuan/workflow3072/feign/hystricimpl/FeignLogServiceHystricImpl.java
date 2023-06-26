package com.qiyuan.workflow3072.feign.hystricimpl;

import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.StringContainerVO;
import com.qiyuan.workflow3072.feign.FeignLogService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
@Data
public class FeignLogServiceHystricImpl implements FeignLogService {
    private Throwable cause;

    @Override
    public ResultDTO sendLog(@Valid StringContainerVO stringContainerVO)  {
        return ResultDTO.failureCustom("服务熔断！");
    }
}
