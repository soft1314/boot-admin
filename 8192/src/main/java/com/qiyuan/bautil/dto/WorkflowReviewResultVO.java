package com.qiyuan.bautil.dto;

import com.qiyuan.bautil.annotate.valid.EnumValid;
import com.qiyuan.bautil.annotate.valid.OverallValid;
import com.qiyuan.bautil.enums.OutComeEnum;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@OverallValid(value = "selfCheck1",message = "驳回时必须输入审核意见。")
public class WorkflowReviewResultVO {
    private String taskId;
    private String reviewComments;
    /** agree or reject  **/
    @NotBlank(message = "审核结论不允许为空。")
    @EnumValid(target = OutComeEnum.class, message = "审核结论只允许出现【通过、驳回、退回起点、撤回、销毁】。")
    private String result;
    private String processInstanceId;
    /** 流转中修改标题用 **/
    private String title;
    /** 审核时流程变量 **/
    private Map<String, Object> vars;
    public boolean selfCheck1(){
        if("驳回".equalsIgnoreCase(result)){
            if(StringUtils.isBlank(reviewComments)){
                return false;
            }
        }
        return true;
    }
}