package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-10-05 10:06
 **/
public enum ResourceAccessControlStyleEnum {
    /** 自由使用，登录访问，指定角色访问 **/
    FREE("01"),ALL_ROLE("02"),GRANT_ROLE("03");

    private final String value;
    ResourceAccessControlStyleEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
