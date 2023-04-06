package com.qiyuan.base2048.pojo.vo.eltable;

import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import lombok.Data;

@Data
public class MenuVO extends TbResource {
    private Boolean hasChildren;
}
