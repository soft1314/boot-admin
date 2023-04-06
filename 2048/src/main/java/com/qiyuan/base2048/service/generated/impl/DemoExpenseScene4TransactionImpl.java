package com.qiyuan.base2048.service.generated.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiyuan.base2048.feign.FeignWorkflowService;
import com.qiyuan.base2048.mapper.mybatis.DemoExpenseMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoExpense;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generated.IDemoExpenseScene4Transaction;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowProcessInstanceDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import com.qiyuan.bautil.enums.WorkflowStatusEnum;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import javax.annotation.Resource;

@Service
@GlobalTransactional(rollbackFor=Exception.class)
public class DemoExpenseScene4TransactionImpl implements IDemoExpenseScene4Transaction {
    @Autowired
    private ObjectMapper objectMapper;
    @Resource
    private FeignWorkflowService feignWorkflowService;
    @Resource
    private DemoExpenseMapper myMapper;

    @Override
    public ResultDTO saveDemoExpenseAndStartProcess(DemoExpense entity, WorkflowStartProcessDTO startProcessDTO) throws Exception {
        if(StringUtils.isNotBlank(entity.getGuid())){
            /** 修改数据，并提交流程 **/
            startProcessDTO.setBusinessId(entity.getGuid());
            ResultDTO resultDTO = feignWorkflowService.startProcess(startProcessDTO);
            if(resultDTO.isSuccess()){
                WorkflowProcessInstanceDTO workflowProcessInstanceDTO = objectMapper.convertValue(resultDTO.getData(),WorkflowProcessInstanceDTO.class);
                entity.setProcessDefinitionId(workflowProcessInstanceDTO.getProcessDefinitionId());
                entity.setProcessInstanceId(workflowProcessInstanceDTO.getProcessInstanceId());
                entity.setFlowStatus(WorkflowStatusEnum.IN_PROGRESS.getValue());
                myMapper.updateById(entity);
            }else{
                throw new Exception(MessageUtils.get("transaction.global.rollback"));
            }
        }else{
            /** 插入数据，并提交流程 **/
            /** 由于主键策略IdType.ASSIGN_ID，这里需先插入数据库，再启动流程，再修改数据**/
            entity.setFlowStatus(WorkflowStatusEnum.INIT.getValue());
            if(myMapper.insert(entity) == 1) {
                startProcessDTO.setBusinessId(entity.getGuid());
                ResultDTO resultDTO = feignWorkflowService.startProcess(startProcessDTO);
                if (resultDTO.isSuccess()) {
                    WorkflowProcessInstanceDTO workflowProcessInstanceDTO = objectMapper.convertValue(resultDTO.getData(), WorkflowProcessInstanceDTO.class);
                    entity.setProcessDefinitionId(workflowProcessInstanceDTO.getProcessDefinitionId());
                    entity.setProcessInstanceId(workflowProcessInstanceDTO.getProcessInstanceId());
                    entity.setFlowStatus(WorkflowStatusEnum.IN_PROGRESS.getValue());
                    myMapper.updateById(entity);
                } else {
                    throw new Exception(MessageUtils.get("transaction.global.rollback"));
                }
            }else{
                throw new Exception(MessageUtils.get("transaction.global.rollback"));
            }
        }
        return ResultDTO.success();
    }

    @Override
    public ResultDTO saveDemoExpenseAndCompleteTask(DemoExpense entity, WorkflowReviewResultVO workflowReviewResultVO) throws Exception {
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
