package com.qiyuan.base2048.pojo.vo.result;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDictionary;
import lombok.Data;

@Data
public class TbDictionaryDataVO extends TbDictionary {
    /** 是否末级 **/
    private boolean hasChildren;
}
