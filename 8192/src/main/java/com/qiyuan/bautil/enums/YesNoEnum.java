package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-22 15:56
 **/
public enum YesNoEnum {
    /**
     * 是否 1是 0否
     */
    YES("1"),NO("0");

    private final String value;
    YesNoEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
