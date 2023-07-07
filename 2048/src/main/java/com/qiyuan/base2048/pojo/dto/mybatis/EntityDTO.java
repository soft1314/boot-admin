package com.qiyuan.base2048.pojo.dto.mybatis;

import lombok.Data;
import java.util.List;

@Data
public class EntityDTO extends MybatisPlusDTO{
    /** 非主键列 **/
    private List<EntityColumnDTO> nonPkColumns;
    /** 主键 **/
    private EntityColumnDTO primaryKey;
}
