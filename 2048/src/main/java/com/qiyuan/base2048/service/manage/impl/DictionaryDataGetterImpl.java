package com.qiyuan.base2048.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mapstruct.TbAdmDivScene2StructMapper;
import com.qiyuan.base2048.mapper.mapstruct.TbDictionaryStructMapper;
import com.qiyuan.base2048.mapper.mapstruct.TbDictypeStructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAdmDiv;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDictionary;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import com.qiyuan.base2048.pojo.vo.query.DictypeQueryVO;
import com.qiyuan.base2048.pojo.vo.query.TbDictionaryQueryVO;
import com.qiyuan.base2048.pojo.vo.result.TbDictionaryDataVO;
import com.qiyuan.base2048.service.manage.DictionaryDataGetter;
import com.qiyuan.base2048.service.mybatis.ITbDictionaryService;
import com.qiyuan.base2048.service.mybatis.ITbDictypeService;
import com.qiyuan.bautil.dto.CascaderNode;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictionaryDataGetterImpl implements DictionaryDataGetter {
    @Resource
    private ITbDictypeService iTbDictypeService;
    @Resource
    private ITbDictionaryService iTbDictionaryService;

    @Override
    public ResultDTO getTbDictypeVOPage(DictypeQueryVO dictypeQueryVO, boolean includeDeleted) throws Exception {
        QueryWrapper<TbDictype> wrapper = new QueryWrapper();
        if(!StringUtils.isBlank(dictypeQueryVO.getName())){
            wrapper.like("name",dictypeQueryVO.getName());
        }
        if(!StringUtils.isBlank(dictypeQueryVO.getCode())){
            wrapper.like("code",dictypeQueryVO.getCode());
        }
        if(!StringUtils.isBlank(dictypeQueryVO.getVarName())){
            wrapper.like("var_name",dictypeQueryVO.getVarName());
        }
        if(!StringUtils.isBlank(dictypeQueryVO.getEnabled())){
            wrapper.eq("enabled",dictypeQueryVO.getEnabled());
        }
        if(!includeDeleted) {
            wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
        }
        wrapper.orderByAsc("code");

        IPage tbDictypeIPage = new Page<>(dictypeQueryVO.getCurrentPage(),dictypeQueryVO.getPageSize());

        tbDictypeIPage = iTbDictypeService.page(tbDictypeIPage,wrapper);
        tbDictypeIPage.setRecords(TbDictypeStructMapper.INSTANCE.toTbDictypeVOList(tbDictypeIPage.getRecords()));
        return ResultDTO.success(tbDictypeIPage);
    }

    @Override
    public ResultDTO getTbDictionaryVOPage(TbDictionaryQueryVO tbDictionaryQueryVO, boolean includeDeleted) throws Exception {
        QueryWrapper<TbDictionary> wrapper = new QueryWrapper();
        wrapper.eq("ITEM_TYPE_CODE",tbDictionaryQueryVO.getItemTypeCode());

        if(!includeDeleted) {
            wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
        }
        wrapper.orderByAsc("SORT");
        IPage tbDictionaryIPage = new Page<>(tbDictionaryQueryVO.getCurrentPage(),tbDictionaryQueryVO.getPageSize());

        tbDictionaryIPage = iTbDictionaryService.page(tbDictionaryIPage,wrapper);
//        tbDictionaryIPage.setRecords(TbDictionaryStructMapper.INSTANCE.map2TbDictionaryVOList(tbDictionaryIPage.getRecords()));
        return ResultDTO.success(tbDictionaryIPage);
    }

    @Override
    public ResultDTO getTopTbDictionaryVOPage(TbDictionaryQueryVO tbDictionaryQueryVO, boolean includeDeleted) throws Exception {
        QueryWrapper<TbDictionary> wrapper = new QueryWrapper();
        wrapper.eq("ITEM_TYPE_CODE",tbDictionaryQueryVO.getItemTypeCode())
                .eq("PARENT_GUID","top");

        if(!includeDeleted) {
            wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
        }
        wrapper.orderByAsc("SORT");
        IPage tbDictionaryIPage = new Page<>(tbDictionaryQueryVO.getCurrentPage(),tbDictionaryQueryVO.getPageSize());

        tbDictionaryIPage = iTbDictionaryService.page(tbDictionaryIPage,wrapper);
        List<TbDictionaryDataVO> tbDictionaryDataVOList = TbDictionaryStructMapper.INSTANCE.map2TbDictionaryDataVOList(tbDictionaryIPage.getRecords());
        for(TbDictionaryDataVO tbDictionaryDataVO:tbDictionaryDataVOList){
            if(YesNoEnum.YES.getValue().equals(tbDictionaryDataVO.getLeaf())){
                tbDictionaryDataVO.setHasChildren(false);
            }else{
                tbDictionaryDataVO.setHasChildren(true);
            }
        }
        tbDictionaryIPage.setRecords(tbDictionaryDataVOList);
        return ResultDTO.success(tbDictionaryIPage);
    }

    @Override
    public ResultDTO getAllChilden(String guid) {
        QueryWrapper<TbDictionary> wrapper = new QueryWrapper();
        wrapper.eq("PARENT_GUID", guid);
        wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
        wrapper.orderByAsc("SORT");

        List<TbDictionary> list = iTbDictionaryService.list(wrapper);
        List<TbDictionaryDataVO> tbDictionaryDataVOList = TbDictionaryStructMapper.INSTANCE.map2TbDictionaryDataVOList(list);
        for(TbDictionaryDataVO tbDictionaryDataVO:tbDictionaryDataVOList){
            if(YesNoEnum.YES.getValue().equals(tbDictionaryDataVO.getLeaf())){
                tbDictionaryDataVO.setHasChildren(false);
            }else{
                tbDictionaryDataVO.setHasChildren(true);
            }
        }
        return ResultDTO.success(tbDictionaryDataVOList);
    }

    @Override
    public List<TbDictype> getValidTbDictypeList() throws Exception {
        QueryWrapper<TbDictype> wrapper = new QueryWrapper();
        wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getValue())
                .orderByAsc("CODE");
        List<TbDictype> tbDictypeList = iTbDictypeService.list(wrapper);
        return tbDictypeList;
    }

    @Override
    public ResultDTO getOneLevelNode(String dicType, String code) throws Exception {
        QueryWrapper<TbDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ITEM_TYPE_CODE",dicType);
        if("top".equals(code)) {
            queryWrapper.eq("PARENT_GUID", "top");
        }else{
            queryWrapper.inSql("PARENT_GUID","select GUID from TB_DICTIONARY where ITEM_CODE='"+code+"' and DELETED='"+IsDeletedEnum.NOTDELETED.getStringValue()+"' and ENABLED='"+IsEnabledEnum.ENABLED.getStringValue()+"'");
        }
        queryWrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .orderByAsc("SORT");
        List<TbDictionary> tbDictionaryList = iTbDictionaryService.list(queryWrapper);
        List<CascaderNode> cascaderNodeList = TbDictionaryStructMapper.INSTANCE.toCascaderNodeList(tbDictionaryList);
        return ResultDTO.success(cascaderNodeList);
    }
}
