package ${marker.packageUrl}.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${marker.mapstructPackageUrl}.${marker.entityName}Scene3StructMapper;
import ${marker.mapperPackageUrl}.${marker.entityName}Mapper;
import ${marker.mapperPackageUrl}.entity.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene3CompleteTaskDataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene3StartProcessDataVO;
import ${marker.packageUrl}.I${marker.entityName}Scene3Service;
import ${marker.packageUrl}.I${marker.entityName}Scene3Transaction;
import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.WorkflowStatusEnum;
import com.qiyuan.bautil.util.WorkflowTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${marker.tableComment} 剧情3服务类
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@Service
public class ${marker.entityName}Scene3ServiceImpl extends ServiceImpl<${marker.entityName}Mapper, ${marker.entityName}>  implements I${marker.entityName}Scene3Service {
  @Autowired
  private I${marker.entityName}Scene3Transaction globalTransaction;

  /**
  * 全局事务
  * @param dataVO
  * @param baseUser
  * @return
  * @throws Exception
  */
  @Override
  public ResultDTO insertBizDataAndStartProcess(${marker.entityName}Scene3StartProcessDataVO dataVO, BaseUser baseUser) throws Exception {
    ${marker.entityName} entity = ${marker.entityName}Scene3StructMapper.INSTANCE.to${marker.entityName}(dataVO);
    if (StringUtils.isBlank(entity.getGuid())) {
     entity = setInitialValueWhenInsert(entity, baseUser);
    }
    WorkflowStartProcessDTO startProcessDTO = this.getStartProcessDTO(entity, dataVO.getProcDefKey(),baseUser);
    ResultDTO resultDTO = globalTransaction.save${marker.entityName}AndStartProcess(entity,startProcessDTO);
    return resultDTO;
  }

  @Override
  public ResultDTO updateBizDataAndCompleteTask(${marker.entityName}Scene3CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception {
    ${marker.entityName} entity = ${marker.entityName}Scene3StructMapper.INSTANCE.to${marker.entityName}(dataVO.getData());
    WorkflowReviewResultVO workflowReviewResultVO = dataVO.getInfo();
    workflowReviewResultVO.setTitle(entity.getTitle());
    workflowReviewResultVO.setVars(this.genProcessVars(entity));
    ResultDTO resultDTO = globalTransaction.save${marker.entityName}AndCompleteTask(entity,workflowReviewResultVO);
    return resultDTO;
  }

  @Override
  public ResultDTO getOne(String businessId) throws Exception {
    ${marker.entityName} entity = this.getById(businessId);
    ${marker.entityName}Scene3StartProcessDataVO wfDemoScene3DataVO = ${marker.entityName}Scene3StructMapper.INSTANCE.to${marker.entityName}Scene3StartProcessDataVO(entity);
    return ResultDTO.success(wfDemoScene3DataVO);
  }

  private ${marker.entityName} setInitialValueWhenInsert(${marker.entityName} entity, BaseUser baseUser) throws Exception {
    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    entity.setVersion(1);
    entity.setFlowStatus(WorkflowStatusEnum.IN_PROGRESS.getValue());
    return entity;
  }

  private WorkflowStartProcessDTO getStartProcessDTO(${marker.entityName} entity, String procDefKey,BaseUser baseUser) {
    WorkflowStartProcessDTO startProcessDTO = new WorkflowStartProcessDTO();
    startProcessDTO.setProcDefKey(procDefKey);
    startProcessDTO.setBusinessTable(entity.getClass().getAnnotation(TableName.class).value());
    startProcessDTO.setBusinessId(entity.getGuid());
    startProcessDTO.setTitle(entity.getTitle());
    startProcessDTO.setVars(new HashMap<>());
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.LAST_ASSIGNEE, WorkflowTool.genAssignee(baseUser));

    /** todo 视情放开下面注释行，或者修改，添加代码，添加流程变量，结合流程定义控制分支流程条件 **/
    //startProcessDTO.getVars().put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
<#list marker.cols as col>
  <#if col.prop == 'money'>
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
  </#if>
  <#if col.prop == 'days'>
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.DAYS,entity.getDays());
  </#if>
</#list>
    return startProcessDTO;
  }
  /**
  * 生成审核任务时的变量，根据业务修改
  * @param entity
  * @return
  * @throws Exception
  */
  private Map genProcessVars(${marker.entityName} entity) throws Exception{
    Map map = new HashMap();
    /** todo 视情放开下面注释行，或者修改，添加代码，添加流程变量，结合流程定义控制分支流程条件 **/
    //    map.put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
<#list marker.cols as col>
  <#if col.prop == 'money'>
    map.put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
  </#if>
  <#if col.prop == 'days'>
    map.put(ConstantWorkflowVarKey.DAYS,entity.getDays());
  </#if>
</#list>
    return map;
  }
}
