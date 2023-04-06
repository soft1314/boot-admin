package com.qiyuan.base2048.service.generator.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.TgFileSummaryScene1StructMapper;
import com.qiyuan.base2048.mapper.mybatis.TgFileSummaryMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TgFileSummary;
import com.qiyuan.base2048.pojo.vo.query.TgFileSummaryScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TgFileSummaryScene1DataVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generator.ITgFileSummaryScene1Service;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.bean.BeanUtil.beanToMap;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 生成文件的摘要记录表 剧情1服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-23 14:20:18
*/
@Service
public class TgFileSummaryScene1ServiceImpl extends ServiceImpl<TgFileSummaryMapper, TgFileSummary>  implements ITgFileSummaryScene1Service {
  @Override
  public ResultDTO getPage(TgFileSummaryScene1QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<TgFileSummary> wrapper = new QueryWrapper();
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
    List orderByCols = new ArrayList();
    orderByCols.add("modify_time");
    orderByCols.add("enabled");
    orderByCols.add("deleted");
    wrapper.orderByDesc(orderByCols);
    IPage<TgFileSummary> page = new Page<>(queryVO.getCurrentPage(),queryVO.getPageSize());
    page = this.page(page,wrapper);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO insertOrUpdate(TgFileSummaryScene1DataVO dataVO, BaseUser baseUser) throws Exception {
    boolean result;
    TgFileSummary entity = TgFileSummaryScene1StructMapper.INSTANCE.toTgFileSummary(dataVO);
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
    UpdateWrapper<TgFileSummary> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
    if(this.update(updateWrapper)){
      return ResultDTO.success();
    }
    return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
  }

  private TgFileSummary setInitialValueWhenInsert(TgFileSummary entity, BaseUser baseUser) throws Exception{
    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    return entity;
  }
}
