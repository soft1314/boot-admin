package com.qiyuan.bautil.enums;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-07 17:01
 **/
public enum FilterModeEnum {
    /**
     * 菜单cmd过滤模式 可修改 提交可撤回 待审核  审核可撤回 查询
     */
    INPUT("input"),AUDIT("audit"),INPUT_BACK("input-back"),AUDIT_BACK("audit-back"),QUERY("query"),HANG("hang"),PUBLISH("publish"),PUBLISHED("published");

    private final String value;
    FilterModeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
