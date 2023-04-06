package com.qiyuan.bautil.enums;

/**
 * @author 虚领顶劲气沉丹田
 */
public enum  PlanQueryEnum {

    /**
     * 按单委托查询 0 挂载委托查询 1 根据计划取消委托协议 2
     */
    BYONE(0),HANG(1),CANCEL(2);

    private final Integer value;
    PlanQueryEnum(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
}
