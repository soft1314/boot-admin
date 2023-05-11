package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.generated.ToNotifyScene1StructMapper;
import com.qiyuan.base2048.mapper.mybatis.ToNotifyMapper;
import com.qiyuan.base2048.mapper.mybatis.TrNotifyDivMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyDiv;
import com.qiyuan.base2048.pojo.vo.generated.ToNotifyScene1DataVO;
import com.qiyuan.base2048.pojo.vo.generated.ToNotifyScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.oa.NotifyAdmDivRelationshipVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.manage.AdmDivDataGetter;
import com.qiyuan.base2048.service.mybatis.ITrNotifyDivService;
import com.qiyuan.base2048.service.oa.IToNotifyScene1Service;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.NotifyTypeEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.hutool.core.bean.BeanUtil.beanToMap;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 通知通告主表 剧情1服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-8 10:38:33
*/
@Service
public class ToNotifyScene1ServiceImpl extends ServiceImpl<ToNotifyMapper, ToNotify>  implements IToNotifyScene1Service {
  @Resource
  private AdmDivDataGetter admDivDataGetter;
  @Resource
  private ITrNotifyDivService iTrNotifyDivService;

  @Override
  public ResultDTO getPage(ToNotifyScene1QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<ToNotify> wrapper = new QueryWrapper();
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
    IPage<ToNotify> page = new Page<>(queryVO.getCurrentPage(),queryVO.getPageSize());
    page = this.page(page,wrapper);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO insertOrUpdate(ToNotifyScene1DataVO dataVO, BaseUser baseUser) throws Exception {
    boolean result;
    ToNotify entity = ToNotifyScene1StructMapper.INSTANCE.toToNotify(dataVO);
    if(StringUtils.isBlank(entity.getGuid())){
      entity = setInitialValueWhenInsert(entity,baseUser);
      result = this.save(entity);
    }else{
      result = this.updateById(entity);
    }
    if(result){
      return ResultDTO.successCustom(MessageUtils.get("dao.save.success"), null);
    }else{
      return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
    }
  }
  @Override
  public ResultDTO delete(String guid) throws Exception {
    UpdateWrapper<ToNotify> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
    if(this.update(updateWrapper)){
      return ResultDTO.success();
    }
    return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
  }

  @Override
  public ResultDTO saveTrNotifyAdmDiv(NotifyAdmDivRelationshipVO notifyAdmDivRelationshipVO) throws Exception {
    /**
     * 对区划编码数组进行精简，有上级编码的，其下级一律丢弃
     * 算法：1.全放在map里，2.遍历编码 3.编码的上级编码未在map中时，放到结果list,否则丢弃 4.遍历结束时得到结果
     */
    List<String> codes = notifyAdmDivRelationshipVO.getAdmDivCodes();
    String notifyGuid = notifyAdmDivRelationshipVO.getNotifyGuid();

    List<String> codesForSave = new ArrayList<>();
    Map map = codes.stream().collect(Collectors.toMap(Function.identity(), s->0));
    for(String code:codes) {
      if(ConstantCommon.ADM_DIV_ROOT_CODE.equals(code)){
        /**
         * 只有有00，就唯一00
         */
        codesForSave = new ArrayList<>();
        codesForSave.add(ConstantCommon.ADM_DIV_ROOT_CODE);
        break;
      }else {
        String fatherCode = admDivDataGetter.getFatherCode(code);
        if (!map.containsKey(fatherCode)) {
          codesForSave.add(code);
        }
      }
    }
    /**
     * 先逻辑删除原记录，再循环插入保存
     */
    this.logicDeleteTrNotifyDivByNotifyGuid(notifyGuid);
    this.batchInsertTrNotifyDiv(notifyGuid,codesForSave);
    return ResultDTO.success();
  }

  private ToNotify setInitialValueWhenInsert(ToNotify entity, BaseUser baseUser) throws Exception{
//    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
//    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    //未发布状态
    entity.setHaveRelease(YesNoEnum.NO.getValue());
    entity.setVersion(1);
    return entity;
  }
  private boolean logicDeleteTrNotifyDivByNotifyGuid(String notifyGuid) throws Exception{
    UpdateWrapper<TrNotifyDiv> updateWrapper = new UpdateWrapper<>();
    updateWrapper.set("DELETED",IsDeletedEnum.DELETED.getStringValue())
            .eq("NOTIFY_GUID",notifyGuid);
    /** UpdateWrapper 不带entity参数，自动填充无效 **/
    boolean result = iTrNotifyDivService.update(new TrNotifyDiv(),updateWrapper);
    return result;
  }
  private boolean batchInsertTrNotifyDiv(String notifyGuid,List<String> admDivCodes) throws Exception{
    List<TrNotifyDiv> trNotifyDivLstForInsert = new ArrayList<>();
    for(String code:admDivCodes){
      TrNotifyDiv trNotifyDiv = new TrNotifyDiv();
      trNotifyDiv.setDivCode(code);
      trNotifyDiv.setNotifyGuid(notifyGuid);
      trNotifyDiv.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
      trNotifyDiv.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
      trNotifyDivLstForInsert.add(trNotifyDiv);
    }

    boolean result = iTrNotifyDivService.saveBatch(trNotifyDivLstForInsert);
    return result;
  }

}
