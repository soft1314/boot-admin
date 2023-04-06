package com.qiyuan.bautil.enums;

import java.math.BigDecimal;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-28 17:54
 **/
public enum YesNoIntegerEnum {
    /**
     * 是否 1是 0否
     */
    YES(1),NO(0);

    private final Integer value;
    YesNoIntegerEnum(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
    public BigDecimal getBigDecimalValue(){
        return new BigDecimal(value);
    }
}
