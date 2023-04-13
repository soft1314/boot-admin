package ${marker.packageUrl}.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${marker.mapstructPackageUrl}.${marker.entityName}Scene2StructMapper;
import ${marker.mapperPackageUrl}.${marker.entityName}Mapper;
import ${marker.entityPackageUrl}.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene2SaveDataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene2RespDataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene2QueryVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import ${marker.packageUrl}.I${marker.entityName}Scene2Service;
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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import static cn.hutool.core.bean.BeanUtil.beanToMap;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${marker.tableComment} 剧情1服务类
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@Service
public class ${marker.entityName}Scene2ServiceImpl extends ServiceImpl<${marker.entityName}Mapper, ${marker.entityName}>  implements I${marker.entityName}Scene2Service {
  @Resource
  private ${marker.entityName}Mapper myMapper;

  @Override
  public ResultDTO getTop(${marker.entityName}Scene2QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<${marker.entityName}> wrapper = new QueryWrapper();
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
    List<${marker.entityName}Scene2RespDataVO> dataVOList = new ArrayList<>();
    for (Object obj : page.getRecords()) {
      ${marker.entityName}Scene2RespDataVO dataVO = ${marker.entityName}Scene2StructMapper.INSTANCE.toRespDataVO((${marker.entityName}) obj);
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
  public ResultDTO getPage(${marker.entityName}Scene2QueryVO queryVO, boolean includeDeleted) throws Exception {
    QueryWrapper<${marker.entityName}> wrapper = new QueryWrapper();
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
    List<${marker.entityName}Scene2RespDataVO> dataVOList = new ArrayList<>();
    for (Object obj : page.getRecords()) {
      ${marker.entityName}Scene2RespDataVO dataVO = ${marker.entityName}Scene2StructMapper.INSTANCE.toRespDataVO((${marker.entityName}) obj);
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
    QueryWrapper<${marker.entityName}> wrapper = new QueryWrapper();
    wrapper.eq("PARENT_GUID", guid);
    wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
    wrapper.orderByAsc("SORT");

    List<${marker.entityName}> list = myMapper.selectList(wrapper);

    List<${marker.entityName}Scene2RespDataVO> dataVOList = ${marker.entityName}Scene2StructMapper.INSTANCE.toRespDataVOList(list);
    for (${marker.entityName}Scene2RespDataVO dataVO : dataVOList) {
      if (YesNoEnum.NO.getValue().equals(dataVO.getLeaf())) {
        dataVO.setHasChildren(true);
      } else {
        dataVO.setHasChildren(false);
      }
    }

    return ResultDTO.success(dataVOList);
  }

  @Override
  public ResultDTO insertOrUpdate(${marker.entityName}Scene2SaveDataVO dataVO, BaseUser baseUser) throws Exception {
    ${marker.entityName} entity = ${marker.entityName}Scene2StructMapper.INSTANCE.to${marker.entityName}(dataVO);
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
    QueryWrapper<${marker.entityName}> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("PARENT_GUID", guid).eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
    if (myMapper.selectCount(queryWrapper) > 0) {
      return ResultDTO.failureCustom(MessageUtils.get("dao.del.error.exists.sub"));
    }
    UpdateWrapper<${marker.entityName}> updateWrapper = new UpdateWrapper();
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
    UpdateWrapper<${marker.entityName}> updateWrapperTbDictionary = new UpdateWrapper();
    updateWrapperTbDictionary.eq("guid",guid)
      .set("LEAF", YesNoEnum.NO.getValue());

    myMapper.update(null,updateWrapperTbDictionary);
  }
}