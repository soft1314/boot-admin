package com.qiyuan.base2048.cache;


import com.qiyuan.bautil.dto.DictionaryNode;
import com.qiyuan.bautil.dto.OptionNode;

import java.util.List;
import java.util.Map;

public interface DictionaryCacheService {
    /**
     * 缓存按字典类别取通用码表
     * @param itemType
     * @return
     * @throws Exception
     */
    List<OptionNode> getDictionaryByType(String itemType) throws Exception;
    List<Map> getOracleObjectTypeOptions() throws Exception;
    /**
     * 缓存按字典类别取通用码表
     */
    List<DictionaryNode> getDictionaryItemByType(int itemType) throws Exception;
}
