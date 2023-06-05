package com.qiyuan.base2048.cache.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.cache.DictionaryCacheService;
import com.qiyuan.base2048.mapper.mapstruct.TbDictionaryStructMapper;
import com.qiyuan.base2048.mapper.mybatis.TbDictionaryMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDictionary;
import com.qiyuan.bautil.dto.DictionaryNode;
import com.qiyuan.bautil.dto.OptionNode;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-02 13:19
 **/
@Slf4j
@Service
public class DictionaryCacheServiceImpl implements DictionaryCacheService {
    @Resource
    private TbDictionaryMapper tbDictionaryMapper;

    @Cacheable(value = "Base:getDictionaryByType", key = "'itemtype:'+#itemtype", unless = "#result==null")
    @Override
    public List<OptionNode> getDictionaryByType(String itemtype) throws Exception {
        log.info("更新缓存 getDictionaryByBype({})",itemtype);
        QueryWrapper<TbDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ITEM_TYPE_CODE",itemtype)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getValue())
                .orderByAsc("SORT")
                .orderByAsc("ITEM_CODE");
        List<TbDictionary> tbDictionaryList = tbDictionaryMapper.selectList(queryWrapper);
        List<OptionNode> optionNodeList = TbDictionaryStructMapper.INSTANCE.map2OptionNodeList(tbDictionaryList);
        return optionNodeList;
    }
    @Override
    @Cacheable(value = "Base:OracleObjectTypeOptions", key = "'()'", unless = "#result==null")
    public List<Map> getOracleObjectTypeOptions() throws Exception {
        log.info("更新缓存 getOracleObjectTypeOptions");
        List optionsList = new ArrayList();
        Map itemMap = new HashMap(2);
        itemMap.put("value", "TABLE");
        itemMap.put("label", "数据表");
        optionsList.add(itemMap);
        itemMap = new HashMap(2);
        itemMap.put("value", "VIEW");
        itemMap.put("label", "视图");
        optionsList.add(itemMap);
        return optionsList;
    }

    @Override
    @Cacheable(value = "Base:getDictionaryItemByType", key = "'itemtype:'+#itemtype", unless = "#result==null")
    public List<DictionaryNode> getDictionaryItemByType(int itemType) throws Exception {
        String strItemType = itemType+"";
        log.info("更新缓存 getDictionaryItemByType({})",strItemType);
        QueryWrapper<TbDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ITEM_TYPE_CODE",strItemType)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getValue())
                .orderByAsc("ITEM_CODE");
        List<TbDictionary> tbDictionaryList = tbDictionaryMapper.selectList(queryWrapper);
        List<DictionaryNode> optionNodeList = TbDictionaryStructMapper.INSTANCE.map2DictionaryNodeList(tbDictionaryList);
        return optionNodeList;
    }
}
