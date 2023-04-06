package com.qiyuan.bautil.enums;

public enum WorkflowStatusEnum {
    /** 00初始状态  01流转中   02已销毁   99已通过   03管理员强制销毁 **/
    INIT("00"),IN_PROGRESS("01"),DESTROYED("02"),FORCE("03"),PASSED("99");

    private final String value;
    WorkflowStatusEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
