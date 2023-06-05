package com.qiyuan.base2048.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.TbDroolsRuleScene1StructMapper;
import com.qiyuan.base2048.mapper.mybatis.TbDroolsRuleMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDroolsRule;
import com.qiyuan.base2048.pojo.vo.query.TbDroolsRuleScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbDroolsRuleScene1DataVO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.manage.ITbDroolsRuleScene1Service;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
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
    * 系统规则表 剧情1服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class TbDroolsRuleScene1ServiceImpl extends ServiceImpl<TbDroolsRuleMapper, TbDroolsRule>  implements ITbDroolsRuleScene1Service {
  @Override
  public ResultDTO getPage(TbDroolsRuleScene1QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<TbDroolsRule> wrapper = new QueryWrapper();
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
    IPage<TbDroolsRule> page = new Page<>(queryVO.getCurrentPage(),queryVO.getPageSize());
    page = this.page(page,wrapper);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO insertOrUpdate(TbDroolsRuleScene1DataVO dataVO, BaseUser baseUser) throws Exception {
    boolean result;
    TbDroolsRule entity = TbDroolsRuleScene1StructMapper.INSTANCE.toTbDroolsRule(dataVO);
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
    UpdateWrapper<TbDroolsRule> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
    if(this.update(updateWrapper)){
      return ResultDTO.success();
    }
    return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
  }

  private TbDroolsRule setInitialValueWhenInsert(TbDroolsRule entity, BaseUser baseUser) throws Exception{
//    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
//    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    entity.setVersion(1);
    return entity;
  }
}
