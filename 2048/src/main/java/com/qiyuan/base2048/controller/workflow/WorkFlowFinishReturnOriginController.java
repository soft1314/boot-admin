package com.qiyuan.base2048.controller.workflow;

import com.qiyuan.base2048.service.workflow.WorkFlowFinishHandler;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/auth/workflow/finish")
@Api("工作流结束时调用的接口")
public class WorkFlowFinishReturnOriginController {
    @Autowired
    private WorkFlowFinishHandler workFlowFinishHandler;

    @GetMapping("/pass")
    @ApiOperation("将指定表指定记录的流程状态置为已通过")
    public ResultDTO passFlow(@RequestParam("businessTable") String businessTable,@RequestParam("businessId") String businessId) throws Exception {
        int count = workFlowFinishHandler.pass(businessTable,businessId);
        return ResultDTO.success(count);
    }
    @GetMapping("/destroy")
    @ApiOperation("将指定表指定记录的流程状态置为已销毁")
    public ResultDTO destroyFlow(@RequestParam("businessTable") String businessTable,@RequestParam("businessId") String businessId) throws Exception {
        int count = workFlowFinishHandler.destroy(businessTable,businessId);
        return ResultDTO.success(count);
    }
    @GetMapping("/force")
    @ApiOperation("将指定表指定记录的流程状态置为已销毁")
    public ResultDTO forceDestroyFlow(@RequestParam("businessTable") String businessTable,@RequestParam("businessId") String businessId) throws Exception {
        int count = workFlowFinishHandler.forceDestroy(businessTable,businessId);
        return ResultDTO.success(count);
    }
}
