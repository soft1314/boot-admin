package ${marker.packageUrl};

import ${marker.entityPackageUrl}.${marker.entityName};
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${marker.tableComment} 剧情4全局事务服务类
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
public interface I${marker.entityName}Scene4Transaction {
    /**
    * 保存${marker.entityName}并启动工作流
    * @param entity
    * @param startProcessDTO
    * @return
    * @throws Exception
    */
    ResultDTO save${marker.entityName}AndStartProcess(${marker.entityName} entity, WorkflowStartProcessDTO startProcessDTO) throws Exception;

    /**
    * 保存${marker.entityName}并完成任务
    * @param entity
    * @param startProcessDTO
    * @return
    * @throws Exception
    */
    ResultDTO save${marker.entityName}AndCompleteTask(${marker.entityName} entity, WorkflowReviewResultVO startProcessDTO) throws Exception;
}
