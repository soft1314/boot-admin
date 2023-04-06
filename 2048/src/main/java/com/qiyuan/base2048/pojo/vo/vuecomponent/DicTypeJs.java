package com.qiyuan.base2048.pojo.vo.vuecomponent;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import lombok.Data;

import java.util.List;

@Data
public class DicTypeJs {
    private String author;
    private String name;
    private List<TbDictype> types;
}
