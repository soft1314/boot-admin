package com.qiyuan.base2048.service.generated.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.generated.DemoOrgScene2StructMapper;
import com.qiyuan.base2048.mapper.mybatis.DemoOrgMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoOrg;
import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2DataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2QueryVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generated.IDemoOrgScene2Service;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.StringTool;
import com.qiyuan.bautil.util.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import static cn.hutool.core.bean.BeanUtil.beanToMap;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（层次CRUD）单位表 剧情1服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:26:50
*/
@Service
public class DemoOrgScene2ServiceImpl extends ServiceImpl<DemoOrgMapper, DemoOrg>  implements IDemoOrgScene2Service {
  @Resource
  private DemoOrgMapper myMapper;

  @Override
  public ResultDTO getTop(DemoOrgScene2QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<DemoOrg> wrapper = new QueryWrapper();
    if (!includeDeleted) {
      wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
    }
    wrapper.eq("PARENT_GUID", "top");
    Map<String, Object> voMap = beanToMap(queryVO, false, true);
    for (String key : voMap.keySet()) {
      if ("currentPage".equalsIgnoreCase(key)) {
       continue;
      }
      if ("pageSize".equalsIgnoreCase(key)) {
        continue;
      }
      String fieldName = StringTool.humpToLine(key);
      /** 根据属性名个性化处理 todo **/
      if ("name".equalsIgnoreCase(fieldName)) {
        String stringValue = voMap.get(key).toString();
        if (StringUtils.isNotBlank(stringValue)) {
          wrapper.like(fieldName, stringValue);
        }
        continue;
      }
      /** 根据类型常规处理 **/
      if ("java.lang.Integer".equalsIgnoreCase(voMap.get(key).getClass().getName())) {
        Integer integerValue = (Integer) voMap.get(key);
        if (integerValue != null) {
          wrapper.eq(fieldName, integerValue);
        }
        continue;
      }
      if ("java.lang.String".equalsIgnoreCase(voMap.get(key).getClass().getName())) {
        String stringValue = voMap.get(key).toString();
        if (StringUtils.isNotBlank(stringValue)) {
          wrapper.eq(fieldName, stringValue);
        }
        continue;
      }
      if ("java.util.Date".equalsIgnoreCase(voMap.get(key).getClass().getName())) {
        Date dateValue = (Date) voMap.get(key);
        if (dateValue != null) {
          Date start = TimeTool.stringToDateTime(TimeTool.stringOfDate(dateValue) + " 00:00:00");
          Date end = TimeTool.stringToDateTime(TimeTool.stringOfDate(dateValue) + " 23:59:59");
          wrapper.between(fieldName, start, end);
        }
        continue;
      }
    }
    wrapper.orderByAsc("SORT");
    IPage page = new Page<>(queryVO.getCurrentPage(), queryVO.getPageSize());
    page = this.page(page, wrapper);
    List<DemoOrgScene2DataVO> dataVOList = new ArrayList<>();
    for (Object obj : page.getRecords()) {
      DemoOrgScene2DataVO dataVO = DemoOrgScene2StructMapper.INSTANCE.toDataVO((DemoOrg) obj);
      if (YesNoEnum.NO.getValue().equals(dataVO.getLeaf())) {
        dataVO.setHasChildren(true);
      } else {
        dataVO.setHasChildren(false);
      }
      dataVOList.add(dataVO);
    }
    page.setRecords(dataVOList);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO getPage(DemoOrgScene2QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<DemoOrg> wrapper = new QueryWrapper();
    if (!includeDeleted) {
      wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
    }
    Map<String, Object> voMap = beanToMap(queryVO, false, true);
    for (String key : voMap.keySet()) {
      if ("currentPage".equalsIgnoreCase(key)) {
        continue;
      }
      if ("pageSize".equalsIgnoreCase(key)) {
        continue;
      }
      String fieldName = StringTool.humpToLine(key);
      /** 根据属性名个性化处理 todo **/
      if ("name".equalsIgnoreCase(fieldName)) {
        String stringValue = voMap.get(key).toString();
        if (StringUtils.isNotBlank(stringValue)) {
          wrapper.like(fieldName, stringValue);
        }
        continue;
      }
      /** 根据类型常规处理 **/
      if ("java.lang.Integer".equalsIgnoreCase(voMap.get(key).getClass().getName())) {
        Integer integerValue = (Integer) voMap.get(key);
        if (integerValue != null) {
          wrapper.eq(fieldName, integerValue);
        }
        continue;
      }
      if ("java.lang.String".equalsIgnoreCase(voMap.get(key).getClass().getName())) {
        String stringValue = voMap.get(key).toString();
        if (StringUtils.isNotBlank(stringValue)) {
          wrapper.eq(fieldName, stringValue);
        }
        continue;
      }
      if ("java.util.Date".equalsIgnoreCase(voMap.get(key).getClass().getName())) {
        Date dateValue = (Date) voMap.get(key);
        if (dateValue != null) {
          Date start = TimeTool.stringToDateTime(TimeTool.stringOfDate(dateValue) + " 00:00:00");
          Date end = TimeTool.stringToDateTime(TimeTool.stringOfDate(dateValue) + " 23:59:59");
          wrapper.between(fieldName, start, end);
        }
        continue;
      }
    }
    wrapper.orderByAsc("SORT");
    IPage page = new Page<>(queryVO.getCurrentPage(), queryVO.getPageSize());
    page = this.page(page, wrapper);
    List<DemoOrgScene2DataVO> dataVOList = new ArrayList<>();
    for (Object obj : page.getRecords()) {
      DemoOrgScene2DataVO dataVO = DemoOrgScene2StructMapper.INSTANCE.toDataVO((DemoOrg) obj);
      if (YesNoEnum.NO.getValue().equals(dataVO.getLeaf())) {
        dataVO.setHasChildren(true);
      } else {
        dataVO.setHasChildren(false);
      }
      dataVOList.add(dataVO);
    }
    page.setRecords(dataVOList);
    return ResultDTO.success(page);
  }
  @Override
  public ResultDTO getAllChilden(String guid) throws Exception {
    QueryWrapper<DemoOrg> wrapper = new QueryWrapper();
    wrapper.eq("PARENT_GUID", guid);
    wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
    wrapper.orderByAsc("SORT");

    List<DemoOrg> list = myMapper.selectList(wrapper);

    List<DemoOrgScene2DataVO> dataVOList = DemoOrgScene2StructMapper.INSTANCE.toDataVOList(list);
    for (DemoOrgScene2DataVO dataVO : dataVOList) {
      if (YesNoEnum.NO.getValue().equals(dataVO.getLeaf())) {
        dataVO.setHasChildren(true);
      } else {
        dataVO.setHasChildren(false);
      }
    }

    return ResultDTO.success(dataVOList);
  }

  @Override
  public ResultDTO insertOrUpdate(DemoOrgScene2DataVO dataVO, BaseUser baseUser) throws Exception {
    DemoOrg entity = DemoOrgScene2StructMapper.INSTANCE.toDemoOrg(dataVO);
    int result = 0;
    if (StringUtils.isBlank(entity.getGuid())) {
      if(entity.getVersion() == null){
        entity.setVersion(0);
      }
      result = myMapper.insert(entity);
      /** 把其上级 leaf = 0 **/
      this.setLeafFalse(entity.getParentGuid());
    } else {
      result = myMapper.updateById(entity);
    }
    if (result == 1) {
      return ResultDTO.success();
    } else {
      return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
    }
  }

  @Override
  public ResultDTO delete(String guid) throws Exception {
    QueryWrapper<DemoOrg> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("PARENT_GUID", guid).eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
    if (myMapper.selectCount(queryWrapper) > 0) {
      return ResultDTO.failureCustom(MessageUtils.get("dao.del.error.exists.sub"));
    }
    UpdateWrapper<DemoOrg> updateWrapper = new UpdateWrapper();
    updateWrapper.eq("guid", guid).set("deleted", IsDeletedEnum.DELETED.getValue());
    if (this.update(updateWrapper)) {
      return ResultDTO.successCustom(MessageUtils.get("dao.delete.sucess"), null);
    } else {
      return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
    }
  }
  private void setLeafFalse(String guid){
    if("top".equals(guid)){
      return;
    }
    UpdateWrapper<DemoOrg> updateWrapperTbDictionary = new UpdateWrapper();
    updateWrapperTbDictionary.eq("guid",guid)
      .set("LEAF", YesNoEnum.NO.getValue());

    myMapper.update(null,updateWrapperTbDictionary);
  }
}