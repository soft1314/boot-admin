package com.qiyuan.bautil.enums;

import java.math.BigDecimal;

/**
 * 是否启用状态值
 *
 * 测试数据库 使用的 1/2  启用1 不启用2
 *
 * 开发数据库使用的 0/1   启用1 不启用2
 *
 * @author 虚领顶劲气沉丹田
 * @date 2020-12-11 10:45
 * @Description:
 */
public enum IsEnabledEnum {
    /**
     * 是否 1是 0否
     */
    ENABLED(1),NOTENABLED(2);

    private final Integer value;
    IsEnabledEnum(Integer value) {
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
