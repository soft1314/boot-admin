package com.qiyuan.workflow3072.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import com.qiyuan.bautil.dto.*;
import com.qiyuan.bautil.util.MapTool;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.UserTool;
import com.qiyuan.bautil.util.WorkflowTool;
import com.qiyuan.workflow3072.pojo.bo.TaskBO;
import com.qiyuan.workflow3072.pojo.vo.HisTaskVO;
import com.qiyuan.workflow3072.pojo.vo.TaskQueryVO;
import com.qiyuan.workflow3072.pojo.vo.TaskVO;
import com.qiyuan.workflow3072.service.MyTaskService;
import com.qiyuan.workflow3072.service.component.FlowableUtis;
import com.qiyuan.workflow3072.service.mapstruct.HisTaskVoTransMapper;
import com.qiyuan.workflow3072.service.transaction.GlobalTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MyTaskServiceImpl implements MyTaskService {
    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;
    @Autowired
    private FlowableUtis flowableUtis;
    @Autowired
    private GlobalTransaction globalTransaction;

    @Override
    public String getFormKey(String procDefId, String taskDefKey) {
        String formKey = "";
        if (StringUtils.isNotBlank(procDefId)) {
            if (StringUtils.isNotBlank(taskDefKey)) {
                try {
                    formKey = formService.getTaskFormData(taskDefKey).getFormKey();
                } catch (Exception e) {
                    formKey = "";
                }
            }
            if (StringUtils.isBlank(formKey)) {
                formKey = formService.getStartFormKey(procDefId);
            }
//            if (StringUtils.isBlank(formKey)) {
//                formKey = "/404";
//            }
        }
        return formKey;
    }

    @Override
    public ResultDTO startProcess(WorkflowStartProcessDTO startProcessDTO) throws Exception {
        BaseUser baseUser = UserTool.getBaseUser();
        /** 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中 **/
        identityService.setAuthenticatedUserId(WorkflowTool.genAssignee(baseUser));
        /** 生成流程变量 **/
        Map vars = this.getVariables(startProcessDTO,baseUser);
        /** 启动流程 **/
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey(startProcessDTO.getProcDefKey(), startProcessDTO.getBusinessId(), vars);
        log.info("启动新流程：{}",procIns.getName());
        WorkflowProcessInstanceDTO workflowProcessInstanceDTO = new WorkflowProcessInstanceDTO();
        workflowProcessInstanceDTO.setProcessDefinitionId(procIns.getProcessDefinitionId());
        workflowProcessInstanceDTO.setProcessInstanceId(procIns.getProcessInstanceId());
        return ResultDTO.success(workflowProcessInstanceDTO);
    }
    private Map getVariables(WorkflowStartProcessDTO startProcessDTO,BaseUser baseUser) throws Exception{
        Map vars = startProcessDTO.getVars();
        if (vars == null) {
            vars = new HashMap();
        }

        /** 设置流程标题  **/
        if (StringUtils.isNotBlank(startProcessDTO.getTitle())) {
            vars.put(ConstantWorkflowVarKey.TITLE, startProcessDTO.getTitle());
        }
        /** 主表名 **/
        if (StringUtils.isNotBlank(startProcessDTO.getBusinessTable())) {
            vars.put(ConstantWorkflowVarKey.BUSINESS_TABLE, startProcessDTO.getBusinessTable());
        }
        /** 主表ID **/
        if (StringUtils.isNotBlank(startProcessDTO.getBusinessId())) {
            vars.put(ConstantWorkflowVarKey.BUSINESS_ID, startProcessDTO.getBusinessId());
        }
        /** 启动人 **/
        vars.put(ConstantWorkflowVarKey.INITIATOR,WorkflowTool.genAssignee(baseUser));
        /** 行政区划 toto**/
        vars.put(ConstantWorkflowVarKey.ADM_DIV_CODE,"370200000");
        /** 单位编码(补齐9位) todo **/
        vars.put(ConstantWorkflowVarKey.ORG_CODE,"101001008");
        return vars;
    }

    @Override
    public ResultDTO getRecallPage(TaskQueryVO queryVO,BaseUser baseUser) throws Exception {
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        int limit = queryVO.getPageSize();

        TaskQuery taskQuery = taskService.createTaskQuery();
        if(StringUtils.isNotBlank(queryVO.getProcessDefinitionName())) {
            taskQuery.processDefinitionNameLike("%"+queryVO.getProcessDefinitionName()+"%");
        }
        if(StringUtils.isNotBlank(queryVO.getTaskName())) {
            taskQuery.taskNameLike("%"+queryVO.getTaskName()+"%");
        }
        if(queryVO.getTaskCreated() != null){
            if(queryVO.getTaskCreated().length == 2) {
                taskQuery.taskCreatedAfter(queryVO.getTaskCreated()[0])
                        .taskCreatedBefore(queryVO.getTaskCreated()[1]);
            }
        }
        if(StringUtils.isNotBlank(queryVO.getTitle())){
            taskQuery.processVariableValueLike(ConstantWorkflowVarKey.TITLE,"%"+queryVO.getTitle()+"%");
        }
        taskQuery.processVariableValueLike("lastAssignee",WorkflowTool.genAssignee(baseUser));

        List<Task> tasks = taskQuery.orderByTaskCreateTime().desc().listPage(offset,limit);
        long total = taskQuery.count();

        List<TaskVO> taskVOS =  new ArrayList<>();
        for(Task task : tasks){
            Map<String, Object> map = taskService.getVariables(task.getId());
            TaskVO taskVO = new TaskVO(task);
            taskVO.setBusinessId(MapTool.getStringValue(map,"businessId"));
            taskVO.setBusinessTable(MapTool.getStringValue(map,"businessTable"));
            taskVO.setTitle(MapTool.getStringValue(map,"title"));

            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            if(processDefinitionList != null && processDefinitionList.size() > 0){
                String processName = processDefinitionList.get(0).getName();
                taskVO.setProcessName(processName);
                String description = processDefinitionList.get(0).getDescription();
                taskVO.setDescription(description);
            }
            taskVOS.add(taskVO);
        }

        Page<TaskVO> page = new Page<>();
        page.setRecords(taskVOS);
        page.setTotal(total);
        return ResultDTO.success(page);
    }
    @Override
    public ResultDTO getTodoPage(TaskQueryVO queryVO,BaseUser baseUser) throws Exception {
        List<String> roles = baseUser.getUserRoles();
        List<String> candidateRoles = new ArrayList<>();
        for(String role:roles){
            if(role.startsWith("ALL_")){
                candidateRoles.add(role);
            } else if(role.startsWith("DIV2_")){
                candidateRoles.add(baseUser.getAdmDivCode().substring(0,2)+"~"+role);
            } else if(role.startsWith("DIV4_")){
                candidateRoles.add(baseUser.getAdmDivCode().substring(0,4)+"~"+role);
            } else if(role.startsWith("DIV6_")){
                candidateRoles.add(baseUser.getAdmDivCode().substring(0,6)+"~"+role);
            } else if(role.startsWith("DIV9_")){
                candidateRoles.add(baseUser.getAdmDivCode().substring(0,9)+"~"+role);
            } else if(role.startsWith("DIV_")){
                candidateRoles.add(baseUser.getAdmDivCode()+"~"+role);
            } else if(role.startsWith("ORG3_")){
                candidateRoles.add(baseUser.getOrgCode().substring(0,3)+"~"+role);
            } else if(role.startsWith("ORG6_")){
                candidateRoles.add(baseUser.getOrgCode().substring(0,6)+"~"+role);
            } else if(role.startsWith("ORG9_")){
                candidateRoles.add(baseUser.getOrgCode().substring(0,9)+"~"+role);
            } else if(role.startsWith("ORG_")){
                candidateRoles.add(baseUser.getOrgCode()+"~"+role);
            }
        }
//        if(candidateRoles.size() == 0){
//            return ResultDTO.failureCustom("用户无审核角色权限。");
//        }
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        int limit = queryVO.getPageSize();
        String assignee = WorkflowTool.genAssignee(baseUser);
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (candidateRoles.size() > 0) {
            taskQuery.or()
                    .taskCandidateGroupIn(candidateRoles)
                    .taskAssignee(assignee)
                    .endOr();
        }else{
            taskQuery.taskAssignee(assignee);
        }
        if(StringUtils.isNotBlank(queryVO.getProcessDefinitionName())) {
            taskQuery.processDefinitionNameLike("%"+queryVO.getProcessDefinitionName()+"%");
        }
        if(StringUtils.isNotBlank(queryVO.getTaskName())) {
            taskQuery.taskNameLike("%"+queryVO.getTaskName()+"%");
        }
        if(queryVO.getTaskCreated() != null){
            if(queryVO.getTaskCreated().length == 2) {
                taskQuery.taskCreatedAfter(queryVO.getTaskCreated()[0])
                        .taskCreatedBefore(queryVO.getTaskCreated()[1]);
            }
        }
        if(StringUtils.isNotBlank(queryVO.getTitle())){
            taskQuery.processVariableValueLike(ConstantWorkflowVarKey.TITLE,"%"+queryVO.getTitle()+"%");
        }

        List<Task> tasks = taskQuery.orderByTaskCreateTime().desc().listPage(offset,limit);
        long total = taskQuery.count();

        List<TaskVO> taskVOS =  new ArrayList<>();
        for(Task task : tasks){
            Map<String, Object> map = taskService.getVariables(task.getId());
            TaskVO taskVO = new TaskVO(task);
            taskVO.setBusinessId(MapTool.getStringValue(map,"businessId"));
            taskVO.setBusinessTable(MapTool.getStringValue(map,"businessTable"));
            taskVO.setTitle(MapTool.getStringValue(map,"title"));

            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            if(processDefinitionList != null && processDefinitionList.size() > 0){
                String processName = processDefinitionList.get(0).getName();
                taskVO.setProcessName(processName);
                String description = processDefinitionList.get(0).getDescription();
                taskVO.setDescription(description);
            }
            taskVOS.add(taskVO);
        }

        Page<TaskVO> page = new Page<>();
        page.setRecords(taskVOS);
        page.setTotal(total);
        return ResultDTO.success(page);
    }

    @Override
    public ResultDTO getAllTodoPage(TaskQueryVO queryVO) throws Exception {
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        int limit = queryVO.getPageSize();

        TaskQuery taskQuery = taskService.createTaskQuery();
        if(StringUtils.isNotBlank(queryVO.getProcessDefinitionName())) {
            taskQuery.processDefinitionNameLike("%"+queryVO.getProcessDefinitionName()+"%");
        }
        if(StringUtils.isNotBlank(queryVO.getTaskName())) {
            taskQuery.taskNameLike("%"+queryVO.getTaskName()+"%");
        }
        if(queryVO.getTaskCreated() != null){
            if(queryVO.getTaskCreated().length == 2) {
                taskQuery.taskCreatedAfter(queryVO.getTaskCreated()[0])
                        .taskCreatedBefore(queryVO.getTaskCreated()[1]);
            }
        }
        if(StringUtils.isNotBlank(queryVO.getTitle())){
            taskQuery.processVariableValueLike(ConstantWorkflowVarKey.TITLE,"%"+queryVO.getTitle()+"%");
        }

        List<Task> tasks = taskQuery.orderByTaskCreateTime().desc().listPage(offset,limit);
        long total = taskQuery.count();

        List<TaskVO> taskVOS =  new ArrayList<>();
        for(Task task : tasks){
            Map<String, Object> map = taskService.getVariables(task.getId());
            TaskVO taskVO = new TaskVO(task);
            taskVO.setBusinessId(MapTool.getStringValue(map,"businessId"));
            taskVO.setBusinessTable(MapTool.getStringValue(map,"businessTable"));
            taskVO.setTitle(MapTool.getStringValue(map,"title"));

            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            if(processDefinitionList != null && processDefinitionList.size() > 0){
                String processName = processDefinitionList.get(0).getName();
                taskVO.setProcessName(processName);
                String description = processDefinitionList.get(0).getDescription();
                taskVO.setDescription(description);
            }
            taskVOS.add(taskVO);
        }

        Page<TaskVO> page = new Page<>();
        page.setRecords(taskVOS);
        page.setTotal(total);
        return ResultDTO.success(page);
    }

    @Override
    public ResultDTO getHisPage(TaskQueryVO queryVO, BaseUser baseUser) throws Exception {
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        int limit = queryVO.getPageSize();
        String assignee = WorkflowTool.genAssignee(baseUser);
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignee);
        /** queryVO.getProcessFinish(),流程结束状态 3全部  6已结束  9流转中（流程未结束） **/
        switch (queryVO.getProcessFinish()){
            case 6:
                historicTaskInstanceQuery.processFinished();
                break;
            case 9:
                historicTaskInstanceQuery.processUnfinished();
                break;
            default:
//                historicTaskInstanceQuery.or().processFinished().processUnfinished().endOr();
                break;
        }
        if(StringUtils.isNotBlank(queryVO.getProcessDefinitionName())) {
            historicTaskInstanceQuery.processDefinitionNameLike("%"+queryVO.getProcessDefinitionName()+"%");
        }
        if(StringUtils.isNotBlank(queryVO.getTaskName())) {
            historicTaskInstanceQuery.taskNameLike("%"+queryVO.getTaskName()+"%");
        }
        if(queryVO.getTaskCreated() != null){
            if(queryVO.getTaskCreated().length == 2) {
                historicTaskInstanceQuery.taskCreatedAfter(queryVO.getTaskCreated()[0])
                        .taskCreatedBefore(queryVO.getTaskCreated()[1]);
            }
        }
        if(queryVO.getTaskCompleted() != null){
            if(queryVO.getTaskCompleted().length == 2) {
                historicTaskInstanceQuery.taskCompletedAfter(queryVO.getTaskCompleted()[0])
                        .taskCompletedBefore(queryVO.getTaskCompleted()[1]);
            }
        }
        if(StringUtils.isNotBlank(queryVO.getTitle())){
            historicTaskInstanceQuery.processVariableValueLike(ConstantWorkflowVarKey.TITLE,"%"+queryVO.getTitle()+"%");
        }
        List<HistoricTaskInstance> historicTaskInstanceList = historicTaskInstanceQuery
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .listPage(offset,limit);
        long total = historicTaskInstanceQuery.count();

        List<HisTaskVO> hisTaskVOList = HisTaskVoTransMapper.INSTANCE.historicTaskInstanceToHisTaskVoList(historicTaskInstanceList);
        for(HisTaskVO hisTaskVO:hisTaskVOList){
            List<Comment> commList = taskService.getTaskComments(hisTaskVO.getId());
            hisTaskVO.setMessage(StringTool.join("；",commList,"message"));
            hisTaskVO.setFullMessage(StringTool.join("；",commList,"fullMessage"));
            /** 通过串截取方式获取办件结果 **/
            hisTaskVO.setOutcome(WorkflowTool.getPrefixStr(hisTaskVO.getMessage(),"【"));
            List<HistoricVariableInstance> historicVariableInstances=historyService.createHistoricVariableInstanceQuery().processInstanceId(hisTaskVO.getProcessInstanceId()).list();
            int count=0;
            for(HistoricVariableInstance historicVariableInstance:historicVariableInstances){
                if(ConstantWorkflowVarKey.BUSINESS_ID.equalsIgnoreCase(historicVariableInstance.getVariableName())){
                    hisTaskVO.setBusinessId(historicVariableInstance.getValue().toString());
                    count++;
                }
                if(ConstantWorkflowVarKey.BUSINESS_TABLE.equalsIgnoreCase(historicVariableInstance.getVariableName())){
                    hisTaskVO.setBusinessTable(historicVariableInstance.getValue().toString());
                    count++;
                }
                if(ConstantWorkflowVarKey.TITLE.equalsIgnoreCase(historicVariableInstance.getVariableName())){
                    if(historicVariableInstance.getValue() == null){
                        hisTaskVO.setTitle("无标题");
                    }else {
                        hisTaskVO.setTitle(historicVariableInstance.getValue().toString());
                    }
                    count++;
                }

                if(count >= 3){
                    break;
                }
            }
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionId(hisTaskVO.getProcessDefinitionId()).list();
            if(processDefinitionList != null && processDefinitionList.size() > 0){
                String processName = processDefinitionList.get(0).getName();
                hisTaskVO.setProcessName(processName);
                String description = processDefinitionList.get(0).getDescription();
                hisTaskVO.setDescription(description);
            }
        }
        Page<HisTaskVO> page = new Page<>();
        page.setRecords(hisTaskVOList);
        page.setTotal(total);
        return ResultDTO.success(page);
    }

    @Override
    public ResultDTO getAllHisPage(TaskQueryVO queryVO, BaseUser baseUser) throws Exception {
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        int limit = queryVO.getPageSize();
        String assignee = WorkflowTool.genAssignee(baseUser);
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        /** queryVO.getProcessFinish(),流程结束状态 3全部  6已结束  9流转中（流程未结束） **/
        switch (queryVO.getProcessFinish()){
            case 6:
                historicTaskInstanceQuery.processFinished();
                break;
            case 9:
                historicTaskInstanceQuery.processUnfinished();
                break;
            default:
//                historicTaskInstanceQuery.or().processFinished().processUnfinished().endOr();
                break;
        }
        if(StringUtils.isNotBlank(queryVO.getProcessDefinitionName())) {
            historicTaskInstanceQuery.processDefinitionNameLike("%"+queryVO.getProcessDefinitionName()+"%");
        }
        if(StringUtils.isNotBlank(queryVO.getTaskName())) {
            historicTaskInstanceQuery.taskNameLike("%"+queryVO.getTaskName()+"%");
        }
        if(queryVO.getTaskCreated() != null){
            if(queryVO.getTaskCreated().length == 2) {
                historicTaskInstanceQuery.taskCreatedAfter(queryVO.getTaskCreated()[0])
                        .taskCreatedBefore(queryVO.getTaskCreated()[1]);
            }
        }
        if(queryVO.getTaskCompleted() != null){
            if(queryVO.getTaskCompleted().length == 2) {
                historicTaskInstanceQuery.taskCompletedAfter(queryVO.getTaskCompleted()[0])
                        .taskCompletedBefore(queryVO.getTaskCompleted()[1]);
            }
        }
        if(StringUtils.isNotBlank(queryVO.getTitle())){
            historicTaskInstanceQuery.processVariableValueLike(ConstantWorkflowVarKey.TITLE,"%"+queryVO.getTitle()+"%");
        }
        List<HistoricTaskInstance> historicTaskInstanceList = historicTaskInstanceQuery
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .listPage(offset,limit);
        long total = historicTaskInstanceQuery.count();

        List<HisTaskVO> hisTaskVOList = HisTaskVoTransMapper.INSTANCE.historicTaskInstanceToHisTaskVoList(historicTaskInstanceList);
        for(HisTaskVO hisTaskVO:hisTaskVOList){
            List<Comment> commList = taskService.getTaskComments(hisTaskVO.getId());
            hisTaskVO.setMessage(StringTool.join("；",commList,"message"));
            hisTaskVO.setFullMessage(StringTool.join("；",commList,"fullMessage"));
            /** 通过串截取方式获取办件结果 **/
            hisTaskVO.setOutcome(WorkflowTool.getPrefixStr(hisTaskVO.getMessage(),"【"));
            List<HistoricVariableInstance> historicVariableInstances=historyService.createHistoricVariableInstanceQuery().processInstanceId(hisTaskVO.getProcessInstanceId()).list();
            int count=0;
            for(HistoricVariableInstance historicVariableInstance:historicVariableInstances){
                if(ConstantWorkflowVarKey.BUSINESS_ID.equalsIgnoreCase(historicVariableInstance.getVariableName())){
                    hisTaskVO.setBusinessId(historicVariableInstance.getValue().toString());
                    count++;
                }
                if(ConstantWorkflowVarKey.BUSINESS_TABLE.equalsIgnoreCase(historicVariableInstance.getVariableName())){
                    hisTaskVO.setBusinessTable(historicVariableInstance.getValue().toString());
                    count++;
                }
                if(ConstantWorkflowVarKey.TITLE.equalsIgnoreCase(historicVariableInstance.getVariableName())){
                    if(historicVariableInstance.getValue() == null){
                        hisTaskVO.setTitle("无标题");
                    }else {
                        hisTaskVO.setTitle(historicVariableInstance.getValue().toString());
                    }
                    count++;
                }

                if(count >= 3){
                    break;
                }
            }
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionId(hisTaskVO.getProcessDefinitionId()).list();
            if(processDefinitionList != null && processDefinitionList.size() > 0){
                String processName = processDefinitionList.get(0).getName();
                hisTaskVO.setProcessName(processName);
                String description = processDefinitionList.get(0).getDescription();
                hisTaskVO.setDescription(description);
            }
        }
        Page<HisTaskVO> page = new Page<>();
        page.setRecords(hisTaskVOList);
        page.setTotal(total);
        return ResultDTO.success(page);
    }

    @Override
    public InputStream genProcessDiagram(String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return null;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 2.0,true);
        return in;
    }

    /**
     * todo ll 待改造为逆向路径，而且未经过，不显示
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @Override
    public InputStream genTracePhoto(String processInstanceId) throws Exception {
        String procDefId;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            procDefId = historicProcessInstance.getProcessDefinitionId();

        } else {
            procDefId = processInstance.getProcessDefinitionId();
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator(); // 创建默认的流程图生成器
        String imageType = "png"; // 生成图片的类型
        List<String> highLightedActivities = new ArrayList<>(); // 高亮节点集合
        List<String> highLightedFlows = new ArrayList<>(); // 高亮连线集合
        List<HistoricActivityInstance> hisActInsList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list(); // 查询所有历史节点信息
        hisActInsList.forEach(historicActivityInstance -> { // 遍历
            if("sequenceFlow".equals(historicActivityInstance.getActivityType())) {
                // 添加高亮连线
                highLightedFlows.add(historicActivityInstance.getActivityId());
            } else {
                // 添加高亮节点
                highLightedActivities.add(historicActivityInstance.getActivityId());
            }
        });
        String activityFontName = "宋体"; // 节点字体
        String labelFontName = "微软雅黑"; // 连线标签字体
        String annotationFontName = "宋体"; // 连线标签字体
        ClassLoader customClassLoader = null; // 类加载器
        double scaleFactor = 1.0d; // 比例因子，默认即可
        boolean drawSequenceFlowNameWithNoLabelDI = true; // 不设置连线标签不会画
        // 生成图片
        InputStream inputStream = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, imageType, highLightedActivities
                , highLightedFlows, activityFontName, labelFontName, annotationFontName, customClassLoader,
                scaleFactor, drawSequenceFlowNameWithNoLabelDI); // 获取输入流
        return inputStream;
    }

    /**
     * 通过任务ID获取表名和业务主键
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public ResultDTO<WorkflowBusinessDataVO> getOneByTaskId(String taskId) throws Exception {
        Map<String, Object> map = taskService.getVariables(taskId);
        WorkflowBusinessDataVO businessDataVO = new WorkflowBusinessDataVO();
        businessDataVO.setId(map.get("businessId")==null?"":map.get("businessId").toString());
        businessDataVO.setTableName(map.get("businessTable")==null?"":map.get("businessTable").toString());
        return ResultDTO.success(businessDataVO);
    }

    /**
     * 事务,因为可能引发监听器调用其他微服务接口，所以要使用全局事务
     * 签收、办件
     * @param reviewResultVO
     * @param baseUser
     * @return
     * @throws Exception
     */
    @Override
//    @Transactional(rollbackFor=Exception.class)
    public ResultDTO completeTask(WorkflowReviewResultVO reviewResultVO, BaseUser baseUser) throws Exception {
        return globalTransaction.completeTask(reviewResultVO,baseUser);
    }

    @Override
    public ResultDTO getLog(String processInstanceId) throws Exception {
        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
        List<TaskBO> taskBOList = new ArrayList<>();
        for(HistoricActivityInstance historicActivityInstance:activityInstances){
            if(!"userTask".equals(historicActivityInstance.getActivityType()) && !"startEvent".equals(historicActivityInstance.getActivityType()) && !"endEvent".equals(historicActivityInstance.getActivityType())){
                continue;
            }
            TaskBO taskBO = new TaskBO();
            taskBO.setStartTime(historicActivityInstance.getStartTime());
            taskBO.setEndTime(historicActivityInstance.getEndTime());
            String assignee=historicActivityInstance.getAssignee();
            taskBO.setAssignee(WorkflowTool.getHumanAssignee(assignee));
            taskBO.setName(historicActivityInstance.getActivityName());
            if(org.apache.commons.lang3.StringUtils.isNotBlank(historicActivityInstance.getTaskId())){
                List<Comment> commList = taskService.getTaskComments(historicActivityInstance.getTaskId());
                String msg = StringTool.join("；",commList,"fullMessage");
                taskBO.setNotes(msg);
            }
            if("startEvent".equals(historicActivityInstance.getActivityType())){
                String initiator = flowableUtis.getInitiatorByProcessInstanceId(historicActivityInstance.getProcessInstanceId());
                String initatorHuman = WorkflowTool.getHumanAssignee(initiator);
                taskBO.setAssignee(initatorHuman);
                taskBO.setNotes("提交");
            }
            if("endEvent".equals(historicActivityInstance.getActivityType())){
                taskBO.setAssignee("系统");
                taskBO.setNotes("完成");
            }
            taskBOList.add(taskBO);
        }
        return ResultDTO.success(taskBOList);
    }
}
