package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-12 17:01
 **/
public enum TableColStyleEnum {
    /**
     * 列类型
     * 普通 normal,
     * 代码Code
     * 日期Date
     */
    NORMAL("normal"),CODE("code"),LAZYCODE("lazycode"),DATE("date");

    private final String value;
    TableColStyleEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
