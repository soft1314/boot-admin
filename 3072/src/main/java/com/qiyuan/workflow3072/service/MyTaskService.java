package com.qiyuan.workflow3072.service;

import com.qiyuan.bautil.dto.*;
import com.qiyuan.workflow3072.pojo.vo.TaskQueryVO;

import java.io.InputStream;

public interface MyTaskService {
    /**
     * 获取流程启动表单
     * @param procDefId
     * @param taskDefKey
     * @return
     * @throws Exception
     */
    String getFormKey(String procDefId, String taskDefKey) throws Exception;

    /**
     * 启动流程
     * @param startProcessDTO
     * @return
     * @throws Exception
     */
    ResultDTO startProcess(WorkflowStartProcessDTO startProcessDTO) throws Exception;

    /**
     * 获取待办任务
     * @param queryVO
     * @return
     * @throws Exception
     */
    ResultDTO getTodoPage(TaskQueryVO queryVO, BaseUser baseUser) throws Exception;

    /**
     * 获取可撤回任务页
     * @param queryVO
     * @param baseUser
     * @return
     * @throws Exception
     */
    ResultDTO getRecallPage(TaskQueryVO queryVO, BaseUser baseUser) throws Exception;

    /**
     * 获取所有待办任务（管理员用）
     * @param queryVO
     * @return
     * @throws Exception
     */
    ResultDTO getAllTodoPage(TaskQueryVO queryVO) throws Exception;

    /**
     * 获取已办任务
     * @param baseUser
     * @return
     * @throws Exception
     */
    ResultDTO getHisPage(TaskQueryVO queryVO, BaseUser baseUser) throws Exception;

    /**
     * 获取所有已办任务（管理员用）
     * @param queryVO
     * @param baseUser
     * @return
     * @throws Exception
     */
    ResultDTO getAllHisPage(TaskQueryVO queryVO, BaseUser baseUser) throws Exception;
    /**
     * 流程图
     * @param processId
     * @return
     * @throws Exception
     */
    InputStream genProcessDiagram(String processId) throws Exception;

    /**
     * 带跟踪的流程图
     * @return
     * @throws Exception
     */
    InputStream genTracePhoto(String processInstanceId) throws Exception;
    /**
     * 根据taskId获取业务表名和主键
     * @param taskId
     * @return
     * @throws Exception
     */
    ResultDTO getOneByTaskId(String taskId) throws Exception;

    /**
     * 审核
     * @param reviewResultVO
     * @return
     * @throws Exception
     */
    ResultDTO completeTask(WorkflowReviewResultVO reviewResultVO, BaseUser baseUser) throws Exception;

    ResultDTO getLog(String processId) throws Exception;
}
