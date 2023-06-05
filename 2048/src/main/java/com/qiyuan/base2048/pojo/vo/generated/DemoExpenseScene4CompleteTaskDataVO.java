package com.qiyuan.base2048.pojo.vo.generated;

import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import lombok.Data;

import javax.validation.Valid;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（业务工作流）报销 工作流任务处理VO
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
public class DemoExpenseScene4CompleteTaskDataVO {
    /** 对应数据 **/
    @Valid
    private DemoExpenseScene4DataVO data;
    /** 对应审核信息 **/
    @Valid
    private WorkflowReviewResultVO info;
}