package com.qiyuan.workflow3072.service.transaction;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;

public interface GlobalTransaction {
    /**
     * 因为完成任务时，有可能引发监听器的执行，而监听器可能调用其他微服务，所以这里需要使用全局事务
     * @param reviewResultVO
     * @param baseUser
     * @return
     * @throws Exception
     */
    ResultDTO completeTask(WorkflowReviewResultVO reviewResultVO, BaseUser baseUser) throws Exception;

    /**
     * 强制销毁流程（管理员使用）
     * @param processInstanceId
     * @param taskId
     * @param businessTable
     * @param businessId
     * @return
     * @throws Exception
     */
    ResultDTO stopProcessInstanceById(String processInstanceId,String taskId,String businessTable,String businessId) throws Exception;

}
