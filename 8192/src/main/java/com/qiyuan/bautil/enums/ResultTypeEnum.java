package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-22 22:56
 **/
public enum ResultTypeEnum {
    /**
     * 是否 1是 0否
     */
    SUCCESS("1"),FAILURE("2");

    private final String value;
    ResultTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
