package com.qiyuan.base2048.service.manage;

import com.qiyuan.base2048.pojo.vo.eltable.TbDictionaryVO;
import com.qiyuan.base2048.pojo.vo.eltable.TbDictypeVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;

public interface DictionaryDataSaver {
    ResultDTO insertOrUpdateTbDictype(TbDictypeVO tbDictypeVO, BaseUser baseUser) throws Exception;
    ResultDTO deleteTbDictype(String guid) throws Exception;

    ResultDTO insertOrUpdateTbDictionary(TbDictionaryVO tbDictionaryVO, BaseUser baseUser) throws Exception;
    ResultDTO deleteTbDictionary(String guid) throws Exception;
}
