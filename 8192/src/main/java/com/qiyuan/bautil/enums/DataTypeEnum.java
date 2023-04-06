package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-12 17:01
 **/
public enum DataTypeEnum {
    /**
     * 数据源名称
     */
    STRING("string"),NUMBER("number"),DATE("date");

    private final String value;
    DataTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
