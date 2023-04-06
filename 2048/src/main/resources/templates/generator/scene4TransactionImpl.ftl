package ${marker.packageUrl}.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiyuan.base2048.feign.FeignWorkflowService;
import ${marker.mapperPackageUrl}.${marker.entityName}Mapper;
import ${marker.mapperPackageUrl}.entity.${marker.entityName};
import com.qiyuan.base2048.service.Component.MessageUtils;
import ${marker.packageUrl}.I${marker.entityName}Scene4Transaction;
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
public class ${marker.entityName}Scene4TransactionImpl implements I${marker.entityName}Scene4Transaction {
    @Autowired
    private ObjectMapper objectMapper;
    @Resource
    private FeignWorkflowService feignWorkflowService;
    @Resource
    private ${marker.entityName}Mapper myMapper;

    @Override
    public ResultDTO save${marker.entityName}AndStartProcess(${marker.entityName} entity, WorkflowStartProcessDTO startProcessDTO) throws Exception {
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
    public ResultDTO save${marker.entityName}AndCompleteTask(${marker.entityName} entity, WorkflowReviewResultVO workflowReviewResultVO) throws Exception {
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
