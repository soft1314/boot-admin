package com.qiyuan.base2048.service.drools.impl;

import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.drools.DroolsLauncher;
import com.qiyuan.bautil.dto.ResultDTO;
import org.apache.commons.lang.StringUtils;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

@Service
public class DoolsLauncherImpl implements DroolsLauncher {
    @Override
    public ResultDTO fireFetch(Object mainObj, Object extraObj, String rule) throws Exception {
        ResultDTO resultDTO = ResultDTO.failure();

        if (StringUtils.isBlank(rule)) {
            return ResultDTO.failureCustom(MessageUtils.get("workflow.procdefkey.null.error"));
        }
        KieHelper helper = new KieHelper();
        helper.addContent(rule, ResourceType.DRL);
        KieBase kieBase = helper.build();
        KieSession kieSession = kieBase.newKieSession();

        kieSession.insert(mainObj);
        if(extraObj!=null){
            kieSession.insert(extraObj);
        }
        kieSession.insert(resultDTO);

        kieSession.fireAllRules();
        kieSession.dispose();
        return resultDTO;
    }

    @Override
    public ResultDTO fireJudge(Object mainObj, Object extraObj, String rule) throws Exception {
        ResultDTO resultDTO = ResultDTO.success();

        if (StringUtils.isBlank(rule)) {
            return resultDTO;
        }
        KieHelper helper = new KieHelper();
        helper.addContent(rule, ResourceType.DRL);
        KieBase kieBase = helper.build();
        KieSession kieSession = kieBase.newKieSession();

        kieSession.insert(mainObj);
        if(extraObj!=null){
            kieSession.insert(extraObj);
        }
        kieSession.insert(resultDTO);

        kieSession.fireAllRules();
        kieSession.dispose();
        return resultDTO;
    }
}
