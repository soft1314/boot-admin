package ${marker.packageUrl}.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${marker.mapstructPackageUrl}.${marker.entityName}Scene4StructMapper;
import ${marker.mapperPackageUrl}.${marker.entityName}Mapper;
import ${marker.entityPackageUrl}.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene4DataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene4QueryVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene4CompleteTaskDataVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import ${marker.packageUrl}.I${marker.entityName}Scene4Service;
import ${marker.packageUrl}.I${marker.entityName}Scene4Transaction;
import com.qiyuan.base2048.service.workflow.ProcDefKeySelector;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.WorkflowStatusEnum;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static cn.hutool.core.bean.BeanUtil.beanToMap;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${marker.tableComment} 剧情4服务类
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@Service
public class ${marker.entityName}Scene4ServiceImpl extends ServiceImpl<${marker.entityName}Mapper, ${marker.entityName}>  implements I${marker.entityName}Scene4Service {
  @Autowired
  private ProcDefKeySelector procDefKeySelector;
  @Autowired
  private I${marker.entityName}Scene4Transaction globalTransaction;

  @Override
  public ResultDTO getPage(${marker.entityName}Scene4QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<${marker.entityName}> wrapper = new QueryWrapper();
    if(!includeDeleted) {
      wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
    }

    Map<String, Object> voMap = beanToMap(queryVO, false, true);
    for (String key : voMap.keySet()) {
      if("currentPage".equalsIgnoreCase(key)){
        continue;
      }
      if("pageSize".equalsIgnoreCase(key)){
        continue;
      }
      String fieldName = StringTool.humpToLine(key);
      /** 根据属性名个性化处理 todo **/
      if("name".equalsIgnoreCase(fieldName)){
        String stringValue = voMap.get(key).toString();
        if(StringUtils.isNotBlank(stringValue)) {
          wrapper.like(fieldName, stringValue);
        }
        continue;
      }
      /** 根据类型常规处理 **/
      if("java.lang.Integer".equalsIgnoreCase(voMap.get(key).getClass().getName())){
        Integer integerValue = (Integer) voMap.get(key);
        if(integerValue != null){
          wrapper.eq(fieldName, integerValue);
        }
        continue;
      }
      if("java.lang.String".equalsIgnoreCase(voMap.get(key).getClass().getName())){
        String stringValue = voMap.get(key).toString();
        if(StringUtils.isNotBlank(stringValue)) {
          wrapper.eq(fieldName, stringValue);
        }
        continue;
      }
      if("java.util.Date".equalsIgnoreCase(voMap.get(key).getClass().getName())){
        Date dateValue = (Date)voMap.get(key);
        if(dateValue != null) {
          Date start = TimeTool.stringToDateTime(TimeTool.stringOfDate(dateValue)+" 00:00:00");
          Date end = TimeTool.stringToDateTime(TimeTool.stringOfDate(dateValue)+" 23:59:59");
          wrapper.between(fieldName, start,end);
        }
        continue;
      }
    }
    IPage<${marker.entityName}> page = new Page<>(queryVO.getCurrentPage(),queryVO.getPageSize());
    page = this.page(page,wrapper);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO insertOrUpdate(${marker.entityName}Scene4DataVO dataVO, BaseUser baseUser) throws Exception {
    boolean result;
    ${marker.entityName} entity = ${marker.entityName}Scene4StructMapper.INSTANCE.to${marker.entityName}(dataVO);
    if(StringUtils.isBlank(entity.getGuid())){
      entity = setInitialValueWhenInsert(entity,baseUser);
      result = this.save(entity);
    }else{
      /** 只有初始态才可以修改 **/
      ${marker.entityName} oldEntity =  this.getById(entity.getGuid());
      if(WorkflowStatusEnum.INIT.getValue().equals(oldEntity.getFlowStatus())) {
        result = this.updateById(entity);
      }else{
        result = false;
      }
    }
    if(result){
      return ResultDTO.successCustom(MessageUtils.get("dao.save.success"), null);
    }else{
      return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
    }
  }
  @Override
  public ResultDTO saveAndStartProcess(${marker.entityName}Scene4DataVO dataVO, BaseUser baseUser) throws Exception {
    ${marker.entityName} entity = ${marker.entityName}Scene4StructMapper.INSTANCE.to${marker.entityName}(dataVO);
    WorkflowStartProcessDTO workflowStartProcessDTO = this.genWorkflowStartProcessDTO(entity,baseUser);
    ResultDTO resultDTO = globalTransaction.save${marker.entityName}AndStartProcess(entity,workflowStartProcessDTO);
    return resultDTO;
  }
  @Override
  public ResultDTO delete(String guid) throws Exception {
    /** 只有初始态才可以删除 **/
    ${marker.entityName} oldEntity =  this.getById(guid);
    if(!WorkflowStatusEnum.INIT.getValue().equals(oldEntity.getFlowStatus())) {
      return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
    }

    UpdateWrapper<${marker.entityName}> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
    if(this.update(updateWrapper)){
      return ResultDTO.success();
    }
    return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
  }

  @Override
  public ResultDTO updateBizDataAndCompleteTask(${marker.entityName}Scene4CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception {
    ${marker.entityName} entity = ${marker.entityName}Scene4StructMapper.INSTANCE.to${marker.entityName}(dataVO.getData());
    WorkflowReviewResultVO workflowReviewResultVO = dataVO.getInfo();
    workflowReviewResultVO.setTitle(entity.getTitle());
    workflowReviewResultVO.setVars(this.genProcessVars(entity));
    ResultDTO resultDTO = globalTransaction.save${marker.entityName}AndCompleteTask(entity,workflowReviewResultVO);
    return resultDTO;
  }

  @Override
  public ResultDTO getOne(String businessId) throws Exception {
    ${marker.entityName} entity = this.getById(businessId);
    ${marker.entityName}Scene4DataVO dataVO = ${marker.entityName}Scene4StructMapper.INSTANCE.to${marker.entityName}Scene4DataVO(entity);
    return ResultDTO.success(dataVO);
  }

  private ${marker.entityName} setInitialValueWhenInsert(${marker.entityName} entity, BaseUser baseUser) throws Exception{
//    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
//    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    entity.setFlowStatus(WorkflowStatusEnum.INIT.getValue());
    entity.setVersion(1);
    return entity;
  }

  private WorkflowStartProcessDTO genWorkflowStartProcessDTO(${marker.entityName} entity,BaseUser baseUser) throws Exception{
    String tableName = entity.getClass().getAnnotation(TableName.class).value();
    WorkflowStartProcessDTO startProcessDTO = new WorkflowStartProcessDTO();
    startProcessDTO.setBusinessTable(tableName);
    /** 不用选择器时，也可以在这里写固定值 **/
    Map map = new HashMap();
    map.put("entity",entity);
    startProcessDTO.setProcDefKey(procDefKeySelector.selectProcDefKey(tableName,map));
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
