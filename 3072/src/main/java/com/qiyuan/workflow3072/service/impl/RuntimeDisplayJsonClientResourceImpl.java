package com.qiyuan.workflow3072.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import com.qiyuan.bautil.util.WorkflowTool;
import com.qiyuan.workflow3072.pojo.bo.TaskBO;
import com.qiyuan.workflow3072.service.InfoMapper;
import com.qiyuan.workflow3072.service.RuntimeDisplayJsonClientResource;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.job.api.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class RuntimeDisplayJsonClientResourceImpl implements RuntimeDisplayJsonClientResource {
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    protected ManagementService managementService;

    protected ObjectMapper objectMapper = new ObjectMapper();
    protected List<String> eventElementTypes = new ArrayList<String>();
    protected Map<String, InfoMapper> propertyMappers = new HashMap<String, InfoMapper>();

    @Override
    public JsonNode getModelJSON(String processInstanceId) throws Exception {
        String processDefinitionId="";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            /** 如果流程已结束，就取历史 **/
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if(historicProcessInstance == null) {
                throw new Exception("No process instance found with id " + processInstanceId);
            }else{
                processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            }
        }else{
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        BpmnModel pojoModel = repositoryService.getBpmnModel(processDefinitionId);

        if (pojoModel == null || pojoModel.getLocationMap().isEmpty()) {
            throw new Exception("Process definition could not be found with id " + processDefinitionId);
        }

        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        Map<String, TaskBO> taskInfo = this.getTaskInfo(activityInstances);
        Set<String> completedActivityInstances = new HashSet<String>();
        Set<String> currentElements = new HashSet<String>();
        if (CollectionUtils.isNotEmpty(activityInstances)) {
            for (HistoricActivityInstance activityInstance : activityInstances) {
                if (activityInstance.getEndTime() != null) {
                    completedActivityInstances.add(activityInstance.getActivityId());
                } else {
                    currentElements.add(activityInstance.getActivityId());
                }
            }
        }

        List<Job> jobs = managementService.createJobQuery().processInstanceId(processInstanceId).list();
        if (CollectionUtils.isNotEmpty(jobs)) {
            List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
            Map<String, Execution> executionMap = new HashMap<String, Execution>();
            for (Execution execution : executions) {
                executionMap.put(execution.getId(), execution);
            }

            for (Job job : jobs) {
                if (executionMap.containsKey(job.getExecutionId())) {
                    currentElements.add(executionMap.get(job.getExecutionId()).getActivityId());
                }
            }
        }

        // Gather completed flows
        List<String> completedFlows = gatherCompletedFlows(completedActivityInstances, currentElements, pojoModel);

        Set<String> completedElements = new HashSet<String>(completedActivityInstances);
        completedElements.addAll(completedFlows);

        ObjectNode displayNode = processProcessElements(pojoModel, completedElements, currentElements,taskInfo);

        if (completedActivityInstances != null) {
            ArrayNode completedActivities = displayNode.putArray("completedActivities");
            for (String completed : completedActivityInstances) {
                completedActivities.add(completed);
            }
        }

        if (currentElements != null) {
            ArrayNode currentActivities = displayNode.putArray("currentActivities");
            for (String current : currentElements) {
                currentActivities.add(current);
            }
        }

        if (completedFlows != null) {
            ArrayNode completedSequenceFlows = displayNode.putArray("completedSequenceFlows");
            for (String current : completedFlows) {
                completedSequenceFlows.add(current);
            }
        }

        return displayNode;

    }
    protected List<String> gatherCompletedFlows(Set<String> completedActivityInstances, Set<String> currentActivityinstances, BpmnModel pojoModel) {

        List<String> completedFlows = new ArrayList<String>();
        List<String> activities = new ArrayList<String>(completedActivityInstances);
        activities.addAll(currentActivityinstances);

        // TODO: not a robust way of checking when parallel paths are active, should be revisited
        // Go over all activities and check if it's possible to match any outgoing paths against the activities
        for (FlowElement activity : pojoModel.getMainProcess().getFlowElements()) {
            if (activity instanceof FlowNode) {
                int index = activities.indexOf(activity.getId());
                if (index >= 0 && index + 1 < activities.size()) {
                    List<SequenceFlow> outgoingFlows = ((FlowNode) activity).getOutgoingFlows();
                    for (SequenceFlow flow : outgoingFlows) {
                        String destinationFlowId = flow.getTargetRef();
                        if (destinationFlowId.equals(activities.get(index + 1))) {
                            completedFlows.add(flow.getId());
                        }
                    }
                }
            }
        }
        return completedFlows;
    }

    /**
     * 加个参数吧，把批注传进来，以MAP形式
     * @param pojoModel
     * @param completedElements
     * @param currentElements
     * @return
     * @throws Exception
     */
    protected ObjectNode processProcessElements(BpmnModel pojoModel, Set<String> completedElements, Set<String> currentElements,Map<String,TaskBO> taskInfo) throws Exception {
        ObjectNode displayNode = objectMapper.createObjectNode();
        GraphicInfo diagramInfo = new GraphicInfo();

        ArrayNode elementArray = objectMapper.createArrayNode();
        ArrayNode flowArray = objectMapper.createArrayNode();

        if (CollectionUtils.isNotEmpty(pojoModel.getPools())) {
            ArrayNode poolArray = objectMapper.createArrayNode();
            boolean firstElement = true;
            for (Pool pool : pojoModel.getPools()) {
                ObjectNode poolNode = objectMapper.createObjectNode();
                poolNode.put("id", pool.getId());
                poolNode.put("name", pool.getName());
                GraphicInfo poolInfo = pojoModel.getGraphicInfo(pool.getId());
                fillGraphicInfo(poolNode, poolInfo, true);
                org.flowable.bpmn.model.Process process = pojoModel.getProcess(pool.getId());
                if (process != null && CollectionUtils.isNotEmpty(process.getLanes())) {
                    ArrayNode laneArray = objectMapper.createArrayNode();
                    for (Lane lane : process.getLanes()) {
                        ObjectNode laneNode = objectMapper.createObjectNode();
                        laneNode.put("id", lane.getId());
                        laneNode.put("name", lane.getName());
                        fillGraphicInfo(laneNode, pojoModel.getGraphicInfo(lane.getId()), true);
                        laneArray.add(laneNode);
                    }
                    poolNode.put("lanes", laneArray);
                }
                poolArray.add(poolNode);

                double rightX = poolInfo.getX() + poolInfo.getWidth();
                double bottomY = poolInfo.getY() + poolInfo.getHeight();
                double middleX = poolInfo.getX() + (poolInfo.getWidth() / 2);
                if (firstElement || middleX < diagramInfo.getX()) {
                    diagramInfo.setX(middleX);
                }
                if (firstElement || poolInfo.getY() < diagramInfo.getY()) {
                    diagramInfo.setY(poolInfo.getY());
                }
                if (rightX > diagramInfo.getWidth()) {
                    diagramInfo.setWidth(rightX);
                }
                if (bottomY > diagramInfo.getHeight()) {
                    diagramInfo.setHeight(bottomY);
                }
                firstElement = false;
            }
            displayNode.put("pools", poolArray);

        } else {
            // in initialize with fake x and y to make sure the minimal
            // values are set
            diagramInfo.setX(9999);
            diagramInfo.setY(1000);
        }

        for (org.flowable.bpmn.model.Process process : pojoModel.getProcesses()) {
            processElements(process.getFlowElements(), pojoModel, elementArray, flowArray, diagramInfo, completedElements, currentElements,taskInfo);
            processArtifacts(process.getArtifacts(), pojoModel, elementArray, flowArray, diagramInfo);
        }

        displayNode.put("elements", elementArray);
        displayNode.put("flows", flowArray);

        displayNode.put("diagramBeginX", diagramInfo.getX());
        displayNode.put("diagramBeginY", diagramInfo.getY());
        displayNode.put("diagramWidth", diagramInfo.getWidth());
        displayNode.put("diagramHeight", diagramInfo.getHeight());
        return displayNode;
    }
    protected void processElements(Collection<FlowElement> elementList, BpmnModel model, ArrayNode elementArray, ArrayNode flowArray, GraphicInfo diagramInfo, Set<String> completedElements,
                                   Set<String> currentElements,Map<String,TaskBO> taskInfo) throws Exception{

        for (FlowElement element : elementList) {
            ObjectNode elementNode = objectMapper.createObjectNode();
            if (completedElements != null) {
                elementNode.put("completed", completedElements.contains(element.getId()));
            }

            if (currentElements != null) {
                elementNode.put("current", currentElements.contains(element.getId()));
            }

            if (element instanceof SequenceFlow) {
                SequenceFlow flow = (SequenceFlow) element;
                elementNode.put("id", flow.getId());
                elementNode.put("type", "sequenceFlow");
                elementNode.put("sourceRef", flow.getSourceRef());
                elementNode.put("targetRef", flow.getTargetRef());
                elementNode.put("name",flow.getName());
                List<GraphicInfo> flowInfo = model.getFlowLocationGraphicInfo(flow.getId());
                if (CollectionUtils.isNotEmpty(flowInfo)) {
                    ArrayNode waypointArray = objectMapper.createArrayNode();
                    for (GraphicInfo graphicInfo : flowInfo) {
                        ObjectNode pointNode = objectMapper.createObjectNode();
                        fillGraphicInfo(pointNode, graphicInfo, false);
                        waypointArray.add(pointNode);
                        fillDiagramInfo(graphicInfo, diagramInfo);
                    }
                    elementNode.put("waypoints", waypointArray);

                    String className = element.getClass().getSimpleName();
                    if (propertyMappers.containsKey(className)) {
                        elementNode.put("properties", propertyMappers.get(className).map(element));
                    }

                    flowArray.add(elementNode);
                }

            } else {

                elementNode.put("id", element.getId());
                elementNode.put("name", element.getName());
                /** 用户任务，添加批注 **/
                if("UserTask".equals(element.getClass().getSimpleName())) {
                    TaskBO taskBO = taskInfo.get(element.getId());
                    if(taskBO != null) {
                        elementNode.put("comments", taskBO.getNotes());
                        if(taskBO.getStartTime() != null) {
                            elementNode.put("startTime", TimeTool.stringOfDateTimeStandardFormat(taskBO.getStartTime()));
                        }
                        if(taskBO.getEndTime() != null) {
                            elementNode.put("endTime", TimeTool.stringOfDateTimeStandardFormat(taskBO.getEndTime()));
                        }
                        elementNode.put("assignee",taskBO.getAssignee());
                    }
                }

                GraphicInfo graphicInfo = model.getGraphicInfo(element.getId());
                if (graphicInfo != null) {
                    fillGraphicInfo(elementNode, graphicInfo, true);
                    fillDiagramInfo(graphicInfo, diagramInfo);
                }

                String className = element.getClass().getSimpleName();
                elementNode.put("type", className);
                fillEventTypes(className, element, elementNode);

                if (element instanceof ServiceTask) {
                    ServiceTask serviceTask = (ServiceTask) element;
                    if (ServiceTask.MAIL_TASK.equals(serviceTask.getType())) {
                        elementNode.put("taskType", "mail");

                    } else if ("camel".equals(serviceTask.getType())) {
                        elementNode.put("taskType", "camel");

                    } else if ("mule".equals(serviceTask.getType())) {
                        elementNode.put("taskType", "mule");
                    }
                }

                if (propertyMappers.containsKey(className)) {
                    elementNode.put("properties", propertyMappers.get(className).map(element));
                }

                elementArray.add(elementNode);

                if (element instanceof SubProcess) {
                    SubProcess subProcess = (SubProcess) element;
                    processElements(subProcess.getFlowElements(), model, elementArray, flowArray, diagramInfo, completedElements, currentElements,taskInfo);
                    processArtifacts(subProcess.getArtifacts(), model, elementArray, flowArray, diagramInfo);
                }
            }
        }
    }
    protected void processArtifacts(Collection<Artifact> artifactList, BpmnModel model, ArrayNode elementArray, ArrayNode flowArray, GraphicInfo diagramInfo) {

        for (Artifact artifact : artifactList) {

            if (artifact instanceof Association) {
                ObjectNode elementNode = objectMapper.createObjectNode();
                Association flow = (Association) artifact;
                elementNode.put("id", flow.getId());
                elementNode.put("type", "association");
                elementNode.put("sourceRef", flow.getSourceRef());
                elementNode.put("targetRef", flow.getTargetRef());
                fillWaypoints(flow.getId(), model, elementNode, diagramInfo);
                flowArray.add(elementNode);

            } else {

                ObjectNode elementNode = objectMapper.createObjectNode();
                elementNode.put("id", artifact.getId());

                if (artifact instanceof TextAnnotation) {
                    TextAnnotation annotation = (TextAnnotation) artifact;
                    elementNode.put("text", annotation.getText());
                }

                GraphicInfo graphicInfo = model.getGraphicInfo(artifact.getId());
                if (graphicInfo != null) {
                    fillGraphicInfo(elementNode, graphicInfo, true);
                    fillDiagramInfo(graphicInfo, diagramInfo);
                }

                String className = artifact.getClass().getSimpleName();
                elementNode.put("type", className);

                elementArray.add(elementNode);
            }
        }
    }
    protected void fillGraphicInfo(ObjectNode elementNode, GraphicInfo graphicInfo, boolean includeWidthAndHeight) {
        commonFillGraphicInfo(elementNode, graphicInfo.getX(), graphicInfo.getY(), graphicInfo.getWidth(), graphicInfo.getHeight(), includeWidthAndHeight);
    }
    protected void commonFillGraphicInfo(ObjectNode elementNode, double x, double y, double width, double height, boolean includeWidthAndHeight) {

        elementNode.put("x", x);
        elementNode.put("y", y);
        if (includeWidthAndHeight) {
            elementNode.put("width", width);
            elementNode.put("height", height);
        }
    }
    protected void fillDiagramInfo(GraphicInfo graphicInfo, GraphicInfo diagramInfo) {
        double rightX = graphicInfo.getX() + graphicInfo.getWidth();
        double bottomY = graphicInfo.getY() + graphicInfo.getHeight();
        double middleX = graphicInfo.getX() + (graphicInfo.getWidth() / 2);
        if (middleX < diagramInfo.getX()) {
            diagramInfo.setX(middleX);
        }
        if (graphicInfo.getY() < diagramInfo.getY()) {
            diagramInfo.setY(graphicInfo.getY());
        }
        if (rightX > diagramInfo.getWidth()) {
            diagramInfo.setWidth(rightX);
        }
        if (bottomY > diagramInfo.getHeight()) {
            diagramInfo.setHeight(bottomY);
        }
    }
    protected void fillEventTypes(String className, FlowElement element, ObjectNode elementNode) {
        if (eventElementTypes.contains(className)) {
            Event event = (Event) element;
            if (CollectionUtils.isNotEmpty(event.getEventDefinitions())) {
                EventDefinition eventDef = event.getEventDefinitions().get(0);
                ObjectNode eventNode = objectMapper.createObjectNode();
                if (eventDef instanceof TimerEventDefinition) {
                    TimerEventDefinition timerDef = (TimerEventDefinition) eventDef;
                    eventNode.put("type", "timer");
                    if (StringUtils.isNotEmpty(timerDef.getTimeCycle())) {
                        eventNode.put("timeCycle", timerDef.getTimeCycle());
                    }
                    if (StringUtils.isNotEmpty(timerDef.getTimeDate())) {
                        eventNode.put("timeDate", timerDef.getTimeDate());
                    }
                    if (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
                        eventNode.put("timeDuration", timerDef.getTimeDuration());
                    }

                } else if (eventDef instanceof ErrorEventDefinition) {
                    ErrorEventDefinition errorDef = (ErrorEventDefinition) eventDef;
                    eventNode.put("type", "error");
                    if (StringUtils.isNotEmpty(errorDef.getErrorCode())) {
                        eventNode.put("errorCode", errorDef.getErrorCode());
                    }

                } else if (eventDef instanceof SignalEventDefinition) {
                    SignalEventDefinition signalDef = (SignalEventDefinition) eventDef;
                    eventNode.put("type", "signal");
                    if (StringUtils.isNotEmpty(signalDef.getSignalRef())) {
                        eventNode.put("signalRef", signalDef.getSignalRef());
                    }

                } else if (eventDef instanceof MessageEventDefinition) {
                    MessageEventDefinition messageDef = (MessageEventDefinition) eventDef;
                    eventNode.put("type", "message");
                    if (StringUtils.isNotEmpty(messageDef.getMessageRef())) {
                        eventNode.put("messageRef", messageDef.getMessageRef());
                    }
                }
                elementNode.put("eventDefinition", eventNode);
            }
        }
    }
    protected void fillWaypoints(String id, BpmnModel model, ObjectNode elementNode, GraphicInfo diagramInfo) {
        List<GraphicInfo> flowInfo = model.getFlowLocationGraphicInfo(id);
        ArrayNode waypointArray = objectMapper.createArrayNode();
        for (GraphicInfo graphicInfo : flowInfo) {
            ObjectNode pointNode = objectMapper.createObjectNode();
            fillGraphicInfo(pointNode, graphicInfo, false);
            waypointArray.add(pointNode);
            fillDiagramInfo(graphicInfo, diagramInfo);
        }
        elementNode.put("waypoints", waypointArray);
    }
    private Map<String, TaskBO> getTaskInfo(List<HistoricActivityInstance> activityInstances) throws Exception{
        Map<String, TaskBO> map = new HashMap();
        for(HistoricActivityInstance historicActivityInstance:activityInstances){
            TaskBO taskBO = new TaskBO();
            taskBO.setStartTime(historicActivityInstance.getStartTime());
            taskBO.setEndTime(historicActivityInstance.getEndTime());
            String assignee=historicActivityInstance.getAssignee();
            String userName= WorkflowTool.getAssigneeUserName(assignee);
            String logonName=WorkflowTool.getAssigneeLogonName(assignee);
            String userGuid=WorkflowTool.getAssigneeUserGuid(assignee);

            if (StringUtils.isNotBlank(userName)) {
                assignee = userName;
                if (StringUtils.isNotBlank(logonName)) {
                    assignee = assignee+"【"+logonName+"】";
                }
            }else{
                if (StringUtils.isNotBlank(logonName)) {
                    assignee = logonName+"【"+userGuid+"】";
                }else{
                    assignee = userGuid;
                }
            }
            taskBO.setAssignee(assignee);
            taskBO.setName(historicActivityInstance.getActivityName());
            if(StringUtils.isNotBlank(historicActivityInstance.getTaskId())){
                List<Comment> commList = taskService.getTaskComments(historicActivityInstance.getTaskId());
                String msg = StringTool.join("；",commList,"fullMessage");
                taskBO.setNotes(msg);
            }
            map.put(historicActivityInstance.getActivityId(),taskBO);
        }

        return map;
    }
}
