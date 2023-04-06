package com.qiyuan.base2048.service.generated;

import com.qiyuan.base2048.mapper.mybatis.entity.DemoExpense;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（业务工作流）报销 剧情4全局事务服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:16:20
*/
public interface IDemoExpenseScene4Transaction {
    /**
    * 保存DemoExpense并启动工作流
    * @param entity
    * @param startProcessDTO
    * @return
    * @throws Exception
    */
    ResultDTO saveDemoExpenseAndStartProcess(DemoExpense entity, WorkflowStartProcessDTO startProcessDTO) throws Exception;

    /**
    * 保存DemoExpense并完成任务
    * @param entity
    * @param startProcessDTO
    * @return
    * @throws Exception
    */
    ResultDTO saveDemoExpenseAndCompleteTask(DemoExpense entity, WorkflowReviewResultVO startProcessDTO) throws Exception;
}
