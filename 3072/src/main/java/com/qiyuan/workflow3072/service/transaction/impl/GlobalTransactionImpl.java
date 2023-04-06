package com.qiyuan.workflow3072.service.transaction.impl;

import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.enums.OutComeEnum;
import com.qiyuan.bautil.util.UserTool;
import com.qiyuan.bautil.util.WorkflowTool;
import com.qiyuan.workflow3072.feign.FeignBaseService;
import com.qiyuan.workflow3072.service.transaction.GlobalTransaction;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@GlobalTransactional(rollbackFor=Exception.class)
public class GlobalTransactionImpl implements GlobalTransaction {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Resource
    private FeignBaseService feignBaseService;

    @Override
    public ResultDTO completeTask(WorkflowReviewResultVO reviewResultVO, BaseUser baseUser) throws Exception {
        Authentication.setAuthenticatedUserId(WorkflowTool.genAssignee(baseUser));
        taskService.addComment(reviewResultVO.getTaskId(), reviewResultVO.getProcessInstanceId(), WorkflowTool.genComment(reviewResultVO.getResult(),reviewResultVO.getReviewComments()));
        taskService.resolveTask(reviewResultVO.getTaskId());
        taskService.claim(reviewResultVO.getTaskId(),WorkflowTool.genAssignee(baseUser));

        Map<String, Object> map = reviewResultVO.getVars();
        if(map == null){
            map = new HashMap<>();
        }
        map.put(ConstantWorkflowVarKey.OUTCOME, reviewResultVO.getResult());
        if(StringUtils.isNotBlank(reviewResultVO.getTitle())) {
            map.put(ConstantWorkflowVarKey.TITLE, reviewResultVO.getTitle());
        }
        if(OutComeEnum.PASS.getValue().equals(reviewResultVO.getResult())) {
            map.put(ConstantWorkflowVarKey.LAST_ASSIGNEE, WorkflowTool.genAssignee(baseUser));
        }else{
            /** 反向路径，胡乱设置 lastAssignee **/
            map.put(ConstantWorkflowVarKey.LAST_ASSIGNEE, "");
        }
        taskService.complete(reviewResultVO.getTaskId(), map);
        return ResultDTO.success();
    }

    @Override
    public ResultDTO stopProcessInstanceById(String processInstanceId, String taskId, String businessTable, String businessId) throws Exception {
        ResultDTO resultDTO = feignBaseService.forceDestroyProcess(businessTable,businessId);
        if(resultDTO.isFailure()){
            throw new Exception("修改业务数据状态时失败！");
        }
        Integer count = (Integer) resultDTO.getData();
        if(count.intValue() != 1){
            throw new Exception("修改业务数据状态时失败！");
        }

        BaseUser baseUser = UserTool.getBaseUser();
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (processInstance != null) {
            //1、添加审批记录
            Authentication.setAuthenticatedUserId(WorkflowTool.genAssignee(baseUser));
            taskService.addComment(taskId,processInstanceId,"强行终止流程。" );
            taskService.resolveTask(taskId);
            taskService.claim(taskId,WorkflowTool.genAssignee(baseUser));

            List<EndEvent> endNodes = this.findEndFlowElement(processInstance.getProcessDefinitionId());
            String endId = endNodes.get(0).getId();
            //2、执行终止
            List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstanceId).list();
            List<String> executionIds = new ArrayList<>();
            executions.forEach(execution -> executionIds.add(execution.getId()));
            this.moveExecutionsToSingleActivityId(executionIds, endId);
            return ResultDTO.success("终止成功");
        }else {
            return ResultDTO.failureCustom("不存在运行的流程实例,请确认!");
        }

    }

    private List<EndEvent> findEndFlowElement(String processDefId) {
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        if (bpmnModel != null) {
            Process process = bpmnModel.getMainProcess();
            return process.findFlowElementsOfType(EndEvent.class);
        } else {
            return null;
        }
    }
    /**
     * 执行跳转
     */
    private void moveExecutionsToSingleActivityId(List<String> executionIds, String activityId) {
        runtimeService.createChangeActivityStateBuilder()
                .moveExecutionsToSingleActivityId(executionIds, activityId)
                .changeState();
    }
    private BpmnModel getBpmnModelByProcessDefId(String processDefId) {
        return repositoryService.getBpmnModel(processDefId);
    }
}
