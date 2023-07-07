package com.qiyuan.base2048.service.generated.impl;

import com.qiyuan.base2048.feign.FeignWorkflowService;
import com.qiyuan.base2048.mapper.mybatis.DemoLeaveMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoLeave;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.generated.IDemoLeaveScene3Transaction;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@GlobalTransactional(rollbackFor=Exception.class)
public class DemoLeaveScene3TransactionImpl implements IDemoLeaveScene3Transaction {
    @Resource
    private FeignWorkflowService feignWorkflowService;
    @Resource
    private DemoLeaveMapper myMapper;

    @Override
    public ResultDTO saveDemoLeaveAndStartProcess(DemoLeave entity, WorkflowStartProcessDTO startProcessDTO) throws Exception {
        if(myMapper.insert(entity) == 1){
            startProcessDTO.setBusinessId(entity.getGuid());
            ResultDTO resultDTO = feignWorkflowService.startProcess(startProcessDTO);
            if(resultDTO.isFailure()){
                throw new Exception(MessageUtils.get("transaction.global.rollback"));
            }
        }
        return ResultDTO.success();
    }

    @Override
    public ResultDTO saveDemoLeaveAndCompleteTask(DemoLeave entity, WorkflowReviewResultVO workflowReviewResultVO) throws Exception {
        if(myMapper.updateById(entity) == 1){
            ResultDTO resultDTO = feignWorkflowService.completeTask(workflowReviewResultVO);
            if(resultDTO.isFailure()){
                throw new Exception(MessageUtils.get("transaction.global.rollback"));
            }
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
        return ResultDTO.success();
    }
}
