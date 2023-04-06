package com.qiyuan.base2048.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mapstruct.TbDictionaryStructMapper;
import com.qiyuan.base2048.mapper.mapstruct.TbDictypeStructMapper;
import com.qiyuan.base2048.mapper.mybatis.TbDictionaryMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDictionary;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import com.qiyuan.base2048.pojo.vo.eltable.TbDictionaryVO;
import com.qiyuan.base2048.pojo.vo.eltable.TbDictypeVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.manage.DictionaryDataSaver;
import com.qiyuan.base2048.service.mybatis.ITbDictionaryService;
import com.qiyuan.base2048.service.mybatis.ITbDictypeService;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class DictionaryDataSaverImpl implements DictionaryDataSaver {
    @Resource
    private ITbDictypeService iTbDictypeService;
    @Resource
    private ITbDictionaryService iTbDictionaryService;
    @Resource
    private TbDictionaryMapper tbDictionaryMapper;

    @Override
    public ResultDTO insertOrUpdateTbDictype(TbDictypeVO tbDictypeVO, BaseUser baseUser) throws Exception {
        TbDictype tbDictype = TbDictypeStructMapper.INSTANCE.toTbDictype(tbDictypeVO);
        boolean result;
        if(StringUtils.isBlank(tbDictype.getGuid())){
            tbDictype = setInitialValueWhenInsertTbDictype(tbDictype,baseUser);
            result = iTbDictypeService.save(tbDictype);
        }else{
            result = iTbDictypeService.updateById(tbDictype);
        }
        if(result){
            return ResultDTO.successCustom(MessageUtils.get("dao.save.success"), null);
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO deleteTbDictype(String guid) throws Exception {
        TbDictype tbDictype = iTbDictypeService.getById(guid);
        if(tbDictype == null){
            return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
        }
        UpdateWrapper<TbDictype> updateWrapperTbDictype = new UpdateWrapper();
        updateWrapperTbDictype.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
        if(iTbDictypeService.update(updateWrapperTbDictype)){
            /** 把子表也要处理 **/
            UpdateWrapper<TbDictionary> updateWrapperTbDictionary = new UpdateWrapper();
            updateWrapperTbDictionary.eq("ITEM_TYPE_CODE",tbDictype.getCode()).set("DELETED", IsDeletedEnum.DELETED.getValue());
            iTbDictionaryService.update(updateWrapperTbDictionary);
            return ResultDTO.successCustom(MessageUtils.get("dao.delete.success"),null);
        }else {
            return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
        }
    }

    @Override
    public ResultDTO insertOrUpdateTbDictionary(TbDictionaryVO tbDictionaryVO, BaseUser baseUser) throws Exception {
        TbDictionary tbDictionary = TbDictionaryStructMapper.INSTANCE.map2TbDictionary(tbDictionaryVO);
        boolean result;
        if(StringUtils.isBlank(tbDictionary.getGuid())){
            tbDictionary = setInitialValueWhenInsertTbDictionary(tbDictionary,baseUser);
            result = iTbDictionaryService.save(tbDictionary);
            /** 把其上级 leaf = 0 **/
            this.setLeafFalse(tbDictionary.getParentGuid());
        }else{
            result = iTbDictionaryService.updateById(tbDictionary);
        }
        if(result){
            return ResultDTO.successCustom(MessageUtils.get("dao.save.success"), null);
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
    }

    @Override
    public ResultDTO deleteTbDictionary(String guid) throws Exception {
        UpdateWrapper<TbDictionary> updateWrapperTbDictionary = new UpdateWrapper();
        updateWrapperTbDictionary.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
        if(iTbDictionaryService.update(updateWrapperTbDictionary)){
            return ResultDTO.successCustom(MessageUtils.get("dao.delete.success"),null);
        }
        return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
    }
    private TbDictype setInitialValueWhenInsertTbDictype(TbDictype tbDictype, BaseUser baseUser) throws Exception{
        tbDictype.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        return tbDictype;
    }
    private TbDictionary setInitialValueWhenInsertTbDictionary(TbDictionary tbDictionary, BaseUser baseUser) throws Exception{
        tbDictionary.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        tbDictionary.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        return tbDictionary;
    }
    private void setLeafFalse(String guid){
        if("top".equals(guid)){
            return;
        }
        UpdateWrapper<TbDictionary> updateWrapperTbDictionary = new UpdateWrapper();
        updateWrapperTbDictionary.eq("guid",guid)
                .set("LEAF", YesNoEnum.NO.getValue());

        iTbDictionaryService.update(updateWrapperTbDictionary);
    }
}
