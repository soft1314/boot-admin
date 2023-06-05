package com.qiyuan.base2048.service.generated.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.generated.DemoExpenseScene4StructMapper;
import com.qiyuan.base2048.mapper.mybatis.DemoExpenseMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoExpense;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4CompleteTaskDataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4DataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4QueryVO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.generated.IDemoExpenseScene4Service;
import com.qiyuan.base2048.service.generated.IDemoExpenseScene4Transaction;
import com.qiyuan.base2048.service.workflow.ProcDefKeySelector;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.WorkflowStatusEnum;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import com.qiyuan.bautil.util.WorkflowTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.hutool.core.bean.BeanUtil.beanToMap;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（业务工作流）报销 剧情4服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class DemoExpenseScene4ServiceImpl extends ServiceImpl<DemoExpenseMapper, DemoExpense>  implements IDemoExpenseScene4Service {
  @Autowired
  private ProcDefKeySelector procDefKeySelector;
  @Autowired
  private IDemoExpenseScene4Transaction globalTransaction;

  @Override
  public ResultDTO getPage(DemoExpenseScene4QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<DemoExpense> wrapper = new QueryWrapper();
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
    IPage<DemoExpense> page = new Page<>(queryVO.getCurrentPage(),queryVO.getPageSize());
    page = this.page(page,wrapper);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO insertOrUpdate(DemoExpenseScene4DataVO dataVO, BaseUser baseUser) throws Exception {
    boolean result;
    DemoExpense entity = DemoExpenseScene4StructMapper.INSTANCE.toDemoExpense(dataVO);
    if(StringUtils.isBlank(entity.getGuid())){
      entity = setInitialValueWhenInsert(entity,baseUser);
      result = this.save(entity);
    }else{
      /** 只有初始态才可以修改 **/
      DemoExpense oldEntity =  this.getById(entity.getGuid());
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
  public ResultDTO saveAndStartProcess(DemoExpenseScene4DataVO dataVO, BaseUser baseUser) throws Exception {
    log.error("GBBASE:saveAndStartProcess测试日志跟踪");
    DemoExpense entity = DemoExpenseScene4StructMapper.INSTANCE.toDemoExpense(dataVO);
    WorkflowStartProcessDTO workflowStartProcessDTO = this.genWorkflowStartProcessDTO(entity,baseUser);
    ResultDTO resultDTO = globalTransaction.saveDemoExpenseAndStartProcess(entity,workflowStartProcessDTO);
    return resultDTO;
  }
  @Override
  public ResultDTO delete(String guid) throws Exception {
    /** 只有初始态才可以删除 **/
    DemoExpense oldEntity =  this.getById(guid);
    if(!WorkflowStatusEnum.INIT.getValue().equals(oldEntity.getFlowStatus())) {
      return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
    }

    UpdateWrapper<DemoExpense> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
    if(this.update(updateWrapper)){
      return ResultDTO.success();
    }
    return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
  }

  @Override
  public ResultDTO updateBizDataAndCompleteTask(DemoExpenseScene4CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception {
    DemoExpense entity = DemoExpenseScene4StructMapper.INSTANCE.toDemoExpense(dataVO.getData());
    WorkflowReviewResultVO workflowReviewResultVO = dataVO.getInfo();
    workflowReviewResultVO.setTitle(entity.getTitle());
    workflowReviewResultVO.setVars(this.genProcessVars(entity));
    ResultDTO resultDTO = globalTransaction.saveDemoExpenseAndCompleteTask(entity,workflowReviewResultVO);
    return resultDTO;
  }

  @Override
  public ResultDTO getOne(String businessId) throws Exception {
    DemoExpense entity = this.getById(businessId);
    DemoExpenseScene4DataVO dataVO = DemoExpenseScene4StructMapper.INSTANCE.toDemoExpenseScene4DataVO(entity);
    return ResultDTO.success(dataVO);
  }

  private DemoExpense setInitialValueWhenInsert(DemoExpense entity, BaseUser baseUser) throws Exception{
//    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
//    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    entity.setFlowStatus(WorkflowStatusEnum.INIT.getValue());
    entity.setVersion(1);
    return entity;
  }

  private WorkflowStartProcessDTO genWorkflowStartProcessDTO(DemoExpense entity,BaseUser baseUser) throws Exception{
    String tableName = entity.getClass().getAnnotation(TableName.class).value();
    Map extraObj=new LinkedHashMap();
    extraObj.put(ConstantWorkflowVarKey.BUSINESS_TABLE,tableName);
    String procDefKey = procDefKeySelector.selectProcDefKey(tableName, ConstantCommon.PARAMETER_NON,entity,extraObj);
    if(StringUtils.isBlank(procDefKey)){
      throw new Exception(MessageUtils.get("workflow.procdefkey.null.error"));
    }
    WorkflowStartProcessDTO startProcessDTO = new WorkflowStartProcessDTO();
    startProcessDTO.setBusinessTable(tableName);
    startProcessDTO.setProcDefKey(procDefKey);
    startProcessDTO.setTitle(entity.getTitle());
    startProcessDTO.setVars(new HashMap<>());
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.LAST_ASSIGNEE, WorkflowTool.genAssignee(baseUser));
    /** todo 视情放开下面注释行，或者修改，添加代码，添加流程变量，结合流程定义控制分支流程条件 **/
    //startProcessDTO.getVars().put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
    return startProcessDTO;
  }
  /**
  * 生成审核任务时的变量，根据业务修改
  * @param entity
  * @return
  * @throws Exception
  */
  private Map genProcessVars(DemoExpense entity) throws Exception{
    Map map = new HashMap();
    /** todo 视情放开下面注释行，或者修改，添加代码，添加流程变量，结合流程定义控制分支流程条件 **/
    //    map.put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
    map.put(ConstantWorkflowVarKey.MONEY,entity.getMoney());

    return map;
  }
}
