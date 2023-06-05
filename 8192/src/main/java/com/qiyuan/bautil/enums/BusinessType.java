package com.qiyuan.bautil.enums;

/**
 * 业务操作类型
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER("01"),

    /**
     * 新增
     */
    INSERT("02"),

    /**
     * 修改
     */
    UPDATE("03"),

    INSERT_OR_UPDATE("04"),

    /**
     * 删除
     */
    DELETE("05"),

    /**
     * 授权
     */
    GRANT("06"),

    /**
     * 导出
     */
    EXPORT("07"),

    /**
     * 导入
     */
    IMPORT("08"),

    /**
     * 强退
     */
    FORCE("09"),

    /**
     * 生成代码
     */
    GENCODE("10"),

    /**
     * 清空数据
     */
    CLEAN("11");

    private final String value;
    BusinessType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
