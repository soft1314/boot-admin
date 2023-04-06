package ${marker.packageUrl}.impl;

import com.qiyuan.base2048.feign.FeignWorkflowService;
import ${marker.mapperPackageUrl}.${marker.entityName}Mapper;
import ${marker.mapperPackageUrl}.entity.${marker.entityName};
import com.qiyuan.base2048.service.Component.MessageUtils;
import ${marker.packageUrl}.I${marker.entityName}Scene3Transaction;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@GlobalTransactional(rollbackFor=Exception.class)
public class ${marker.entityName}Scene3TransactionImpl implements I${marker.entityName}Scene3Transaction {
    @Resource
    private FeignWorkflowService feignWorkflowService;
    @Resource
    private ${marker.entityName}Mapper myMapper;

    @Override
    public ResultDTO save${marker.entityName}AndStartProcess(${marker.entityName} entity, WorkflowStartProcessDTO startProcessDTO) throws Exception {
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
