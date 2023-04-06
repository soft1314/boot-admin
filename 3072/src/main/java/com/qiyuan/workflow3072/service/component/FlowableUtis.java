package com.qiyuan.workflow3072.service.component;

import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import org.flowable.engine.HistoryService;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlowableUtis {
    @Autowired
    private HistoryService historyService;
    public String getInitiatorByProcessInstanceId(String processInstanceId){
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        for(HistoricVariableInstance historicVariableInstance:historicVariableInstanceList){
            if(ConstantWorkflowVarKey.INITIATOR.equals(historicVariableInstance.getVariableName())){
                return historicVariableInstance.getValue().toString();
            }
        }
        return "";
    }
}
