package com.qiyuan.base2048.feign;

import com.qiyuan.base2048.feign.config.FeignConfig;
import com.qiyuan.base2048.feign.factory.FeignWorkflowServiceHystricImplFactory;
import com.qiyuan.bautil.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(contextId = "feignWorkflowService" ,value = "service-rest-workflow3072", configuration = FeignConfig.class, fallbackFactory = FeignWorkflowServiceHystricImplFactory.class)
public interface FeignWorkflowService {
    /** 启动流程 **/
    @PostMapping(value = "/api/workflow/auth/activiti/task/process/start")
    ResultDTO startProcess(@RequestBody @Valid WorkflowStartProcessDTO startProcessDTO) throws Exception;


    /** 通过任务获取流程变量 **/
    @PostMapping(value = "/api/workflow/auth/activiti/task/business/vars")
    ResultDTO<WorkflowBusinessDataVO> fetchBusinessVarsByTaskId(@RequestBody @Valid GuidContainerVO guidContainerVO) throws Exception;

    /** 完成任务 **/
    @PostMapping(value = "/api/workflow/auth/activiti/task/complete")
    ResultDTO completeTask(@RequestBody @Valid WorkflowReviewResultVO workflowReviewResultVO) throws Exception;
}