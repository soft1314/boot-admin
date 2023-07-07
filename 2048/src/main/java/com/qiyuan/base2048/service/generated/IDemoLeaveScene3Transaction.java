package com.qiyuan.base2048.service.generated;

import com.qiyuan.base2048.mapper.mybatis.entity.DemoLeave;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本工作流）请假 剧情3服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface IDemoLeaveScene3Transaction {
    /**
    * 保存DemoLeave并启动工作流
    * @param entity
    * @param startProcessDTO
    * @return
    * @throws Exception
    */
    ResultDTO saveDemoLeaveAndStartProcess(DemoLeave entity, WorkflowStartProcessDTO startProcessDTO) throws Exception;

    /**
    * 保存DemoLeave并完成任务
    * @param entity
    * @param startProcessDTO
    * @return
    * @throws Exception
    */
    ResultDTO saveDemoLeaveAndCompleteTask(DemoLeave entity, WorkflowReviewResultVO startProcessDTO) throws Exception;
}
