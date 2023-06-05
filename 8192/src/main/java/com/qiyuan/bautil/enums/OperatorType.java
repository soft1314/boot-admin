package com.qiyuan.bautil.enums;

/**
 * 操作人类别
 */
public enum OperatorType {
    /**
     * 其它
     */
    OTHER("01"),

    /**
     * 后台用户
     */
    MANAGE("02"),

    /**
     * 手机端用户
     */
    MOBILE("03");
    private final String value;
    OperatorType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
