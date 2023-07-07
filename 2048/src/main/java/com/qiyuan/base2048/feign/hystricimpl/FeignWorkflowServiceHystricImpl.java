package com.qiyuan.base2048.feign.hystricimpl;

import com.qiyuan.base2048.feign.FeignWorkflowService;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
@Data
public class FeignWorkflowServiceHystricImpl implements FeignWorkflowService {
    private Throwable cause;

    @Override
    public ResultDTO startProcess(@Valid WorkflowStartProcessDTO startProcessDTO) throws Exception {
        return null;
    }

    @Override
    public ResultDTO fetchBusinessVarsByTaskId(@Valid GuidContainerVO guidContainerVO) throws Exception {
        return null;
    }

    @Override
    public ResultDTO completeTask(@Valid WorkflowReviewResultVO workflowReviewResultVO) throws Exception {
        return null;
    }
}
