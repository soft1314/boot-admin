package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-12 17:01
 **/
public enum DataSourceEnum {
    /**
     * 数据源名称
     */
    MASTER("master"),SLAVE1("slave1");

    private final String value;
    DataSourceEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
