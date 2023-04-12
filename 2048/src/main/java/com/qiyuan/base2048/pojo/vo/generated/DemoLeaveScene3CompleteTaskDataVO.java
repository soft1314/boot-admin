package com.qiyuan.base2048.pojo.vo.generated;

import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import lombok.Data;

import javax.validation.Valid;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本工作流）请假 工作流任务处理VO
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:27:24
*/

@Data
public class DemoLeaveScene3CompleteTaskDataVO {
    /** 对应数据 **/
    @Valid
    private DemoLeaveScene3StartProcessDataVO data;
    /** 对应审核信息 **/
    @Valid
    private WorkflowReviewResultVO info;
}