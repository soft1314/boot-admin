package com.qiyuan.base2048.service.workflow.impl;

import com.qiyuan.base2048.service.workflow.ProcDefKeySelector;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class ProcDefKeySelectorImpl implements ProcDefKeySelector {
    @Override
    public String selectProcDefKey(String businessTable, Map map) throws Exception {
        /** todo 拟使用Drools规则决断流程KEY **/
        if("DEMO_EXPENSE".equalsIgnoreCase(businessTable)){
            return "expense_process";
        }else if("".equalsIgnoreCase(businessTable)){
            return "leave_process";
        }
        return "process";
    }
}
