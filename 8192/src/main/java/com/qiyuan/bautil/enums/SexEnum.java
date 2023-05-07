package com.qiyuan.bautil.enums;

public enum SexEnum {
    男("1"),女("2");
    private final String value;
    SexEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
