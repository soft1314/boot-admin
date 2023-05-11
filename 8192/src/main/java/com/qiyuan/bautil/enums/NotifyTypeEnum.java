package com.qiyuan.bautil.enums;

/**
 * 通知通告类别
 * 01   会议通知
 * 02   奖惩通告
 * 03   活动通告
 */
public enum NotifyTypeEnum {
    MEETING("01"),REWARD_PUNISHMENT("02"),EVENT("03");

    private final String value;
    NotifyTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
