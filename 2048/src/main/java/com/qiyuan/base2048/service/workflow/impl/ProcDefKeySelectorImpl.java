package com.qiyuan.base2048.service.workflow.impl;

import com.qiyuan.base2048.service.drools.DroolsLauncher;
import com.qiyuan.base2048.service.drools.DroolsRuleGetter;
import com.qiyuan.base2048.service.workflow.ProcDefKeySelector;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProcDefKeySelectorImpl implements ProcDefKeySelector {
    @Autowired
    private DroolsRuleGetter droolsRuleGetter;
    @Autowired
    private DroolsLauncher droolsLauncher;

    @Override
    public String selectProcDefKey(String businessTable,String admDivCode, Object mainObj, Object extraObj) throws Exception {
        /** todo 拟使用Drools规则决断流程KEY **/
//        if("DEMO_EXPENSE".equalsIgnoreCase(businessTable)){
//            return "expense_process";
//        }else if("".equalsIgnoreCase(businessTable)){
//            return "leave_process";
//        }
        /** 从表中取规则 **/
        String rule = droolsRuleGetter.getDroolsRuleByName(ConstantCommon.RULE_NAME_FOR_SELECT_PROCDEFKEY,admDivCode);
        /** 执行规则，获取结果 **/
        ResultDTO resultDTO = droolsLauncher.fireFetch(mainObj,extraObj,rule);
        if(resultDTO.isSuccess()){
            return resultDTO.getData().toString();
        }
        return "";
    }
}
