package com.qiyuan.base2048.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system/auth/idworker")
@ResponseBodyInController
@Slf4j
@Api("分布式ID获取相关接口")
public class IdWorkerController {
    @PostMapping("/idstr")
    public ResultDTO getIdStr() throws Exception{
        return ResultDTO.success(IdWorker.getIdStr());
    }
}
