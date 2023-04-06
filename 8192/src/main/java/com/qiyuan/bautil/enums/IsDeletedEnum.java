package com.qiyuan.bautil.enums;

import java.math.BigDecimal;

/**
 * 是否删除状态值
 *
 * 测试数据库 使用的 1/2  未删除1 删除2
 *
 * 开发数据库使用的 0/1   未删除0 删除1
 *
 * @author 虚领顶劲气沉丹田
 * @date 2020-12-11 10:45
 * @Description:
 */
public enum IsDeletedEnum {
    /**
     * 是否 1是 0否
     */
    DELETED(2),NOTDELETED(1);

    private final Integer value;
    IsDeletedEnum(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
    public BigDecimal getBigDecimalValue(){
        return  new BigDecimal(value);
    }
    public String getStringValue(){
        return value.toString();
    }
}
