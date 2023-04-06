package com.qiyuan.workflow3072.service.handler;

import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import com.qiyuan.workflow3072.feign.FeignBaseService;
import com.qiyuan.workflow3072.utils.GetBean;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

@Slf4j
public class FinishFlowDestroyHandler implements ExecutionListener {
    /** 字段注入使用DEMO  **/
//    private Expression money;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        /**
         * 调用其他微服务，将业务数据状态设置一下
         * sequenceFlow上的执行监听器，在终审pass时将业务数据状态设置为【已通过】
         */
        String businessTable = delegateExecution.getVariable(ConstantWorkflowVarKey.BUSINESS_TABLE).toString();
        String businessId = delegateExecution.getVariable(ConstantWorkflowVarKey.BUSINESS_ID).toString();
        FeignBaseService feignBaseService = GetBean.getBean(FeignBaseService.class);
        feignBaseService.destroyProcess(businessTable,businessId);
        /** 字段注入使用DEMO  **/
//        log.info(money.getValue(delegateExecution).toString());
        /** 构造运行时错误，测试全局事务 **/
//        int a=10/0;
    }
}
