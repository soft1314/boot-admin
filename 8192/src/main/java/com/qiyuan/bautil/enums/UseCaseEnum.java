package com.qiyuan.bautil.enums;

public enum UseCaseEnum {
    /**
     * 查询DTO适用情形  forInput 用于输入 forQuery用于查询
     */
    FOR_INPUT("forInput"),FOR_QUERY("forQuery");

    private final String value;
    UseCaseEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
