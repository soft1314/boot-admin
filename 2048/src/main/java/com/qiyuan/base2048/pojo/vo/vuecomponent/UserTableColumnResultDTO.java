package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserTableColumnResultDTO {
    private String columnName;
    private String dataType;
    private String dataLength;
    private String dataScale;
    private BigDecimal charLength;
    private String nullable;
    private String dataDefault="";
    private String comments;
    private String tableName;
    private String primaryKey;
}
