package com.qiyuan.base2048.service.manage;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import com.qiyuan.base2048.pojo.vo.query.DictypeQueryVO;
import com.qiyuan.base2048.pojo.vo.query.TbDictionaryQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

import java.util.List;

public interface DictionaryDataGetter {
    ResultDTO getTbDictypeVOPage(DictypeQueryVO dictypeQueryVO, boolean includeDeleted) throws Exception;
    ResultDTO getTbDictionaryVOPage(TbDictionaryQueryVO tbDictionaryQueryVO, boolean includeDeleted) throws Exception;
    ResultDTO getTopTbDictionaryVOPage(TbDictionaryQueryVO tbDictionaryQueryVO, boolean includeDeleted) throws Exception;
    ResultDTO getAllChilden(String guid);
    List<TbDictype> getValidTbDictypeList() throws Exception;
    ResultDTO getOneLevelNode(String dicType,String code) throws Exception;
}
