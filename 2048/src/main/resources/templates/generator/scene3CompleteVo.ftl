package ${entity.packageUrl};

import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import lombok.Data;

import javax.validation.Valid;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${entity.tableComment} 工作流任务处理VO
    * </p>
*
* @author ${entity.author}
* @since ${.now}
*/

@Data
public class ${entity.entityName}Scene3CompleteTaskDataVO {
    /** 对应数据 **/
    @Valid
    private ${entity.entityName}Scene3StartProcessDataVO data;
    /** 对应审核信息 **/
    @Valid
    private WorkflowReviewResultVO info;
}