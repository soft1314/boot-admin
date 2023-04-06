package com.qiyuan.base2048.pojo.vo.workflow;

import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import lombok.Data;

import javax.validation.Valid;


@Data
public class WfDemo1Scene3CompleteTaskDataVO {
    /** 对应数据 **/
    @Valid
    private WfDemo1Scene3StartProcessDataVO data;
    /** 对应审核信息 **/
    @Valid
    private WorkflowReviewResultVO info;
}
