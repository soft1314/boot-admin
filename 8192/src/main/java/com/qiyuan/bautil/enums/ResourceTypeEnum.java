package com.qiyuan.bautil.enums;

public enum ResourceTypeEnum {
    /** 资源类别  1菜单 2功能点 **/
    MENU("1"),FUNCTION("2");

    private final String value;
    ResourceTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
