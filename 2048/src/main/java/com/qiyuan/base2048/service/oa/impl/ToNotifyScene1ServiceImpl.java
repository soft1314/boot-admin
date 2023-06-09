package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.ToNotifyScene1StructMapper;
import com.qiyuan.base2048.mapper.mybatis.ToNotifyMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import com.qiyuan.base2048.pojo.vo.save.ToNotifyScene1DataVO;
import com.qiyuan.base2048.pojo.vo.query.ToNotifyScene1QueryVO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.oa.IToNotifyScene1Service;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static cn.hutool.core.bean.BeanUtil.beanToMap;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 通知通告主表 剧情1服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class ToNotifyScene1ServiceImpl extends ServiceImpl<ToNotifyMapper, ToNotify>  implements IToNotifyScene1Service {
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
    wrapper.orderByAsc("HAVE_RELEASE")
    .orderByDesc("RELEASE_TIME")
    .orderByDesc("CREATE_TIME");
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
      ToNotify oldEntity = this.getById(entity.getGuid());
      ResultDTO resultDTO = this.checkForUpdate(oldEntity);
      if(resultDTO.isFailure()){
        return resultDTO;
      }
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
    ToNotify oldEntity = this.getById(guid);
    ResultDTO resultDTO = this.checkForUpdate(oldEntity);
    if(resultDTO.isFailure()){
      return resultDTO;
    }

    UpdateWrapper<ToNotify> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid",guid)
            .eq("HAVE_RELEASE",YesNoEnum.NO.getValue())
            .set("deleted", IsDeletedEnum.DELETED.getValue());
    if(this.update(updateWrapper)){
      return ResultDTO.success();
    }
    return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
  }

  private ToNotify setInitialValueWhenInsert(ToNotify entity, BaseUser baseUser) throws Exception{
//    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
//    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    //未发布状态
    entity.setHaveRelease(YesNoEnum.NO.getValue());
    entity.setVersion(1);
    return entity;
  }
  private ResultDTO checkForUpdate(ToNotify toNotify){
    if(toNotify == null){
      return ResultDTO.failureCustom("找不到数据。");
    }
    if(YesNoEnum.YES.getValue().equals(toNotify.getHaveRelease())){
      return ResultDTO.failureCustom("已发布的通知通告不允许删除和修改。");
    }
    return ResultDTO.success();
  }
}
