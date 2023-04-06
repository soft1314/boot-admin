package com.qiyuan.bautil.enums;

public enum TokenModeEnum {
    JWT("jwt"),REDIS("redis");

    private final String value;
    TokenModeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
