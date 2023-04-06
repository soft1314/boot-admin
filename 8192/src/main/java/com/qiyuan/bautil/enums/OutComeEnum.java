package com.qiyuan.bautil.enums;

public enum OutComeEnum {
    /**
     * 工作流任务办理结论范围类型
     */
    PASS("通过"),REJECT("驳回"),SEND_TO_START("退回起点"),REVOKE("撤回"),DESTROY("销毁");

    private final String value;
    OutComeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
