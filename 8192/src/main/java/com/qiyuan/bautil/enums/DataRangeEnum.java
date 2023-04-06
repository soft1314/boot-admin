package com.qiyuan.bautil.enums;

public enum DataRangeEnum {
    /**
     * 数据权限范围类型
     */
    ALL("01"),SELF("02"),SELFANDLOWER("03"),CUSTOM("04");

    private final String value;
    DataRangeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
