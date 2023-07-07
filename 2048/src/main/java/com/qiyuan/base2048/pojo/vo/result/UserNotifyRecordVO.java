package com.qiyuan.base2048.pojo.vo.result;

import lombok.Data;

import java.util.Date;

@Data
public class UserNotifyRecordVO {
    /**
     *  姓名
     */
    private String firstName;
    /**
     *  别名
     */
    private String lastName;
    /**
     *  登录名
     */
    private String logonName;
    /**
     *  区划编码
     */
    private String admDivCode;
    /**
     *  区划名称
     */
    private String admDivName;
    /**
     *  机构编码
     */
    private String orgCode;
    /**
     *  机构编码
     */
    private String orgName;
    /**
     *  通知时间
     */
    private Date createTime;
    /**
     *  是否已阅;YESNO
     */
    private String haveRead;
    /**
     *  阅读时间
     */
    private Date readTime;
}
