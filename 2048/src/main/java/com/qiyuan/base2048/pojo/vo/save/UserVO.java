package com.qiyuan.base2048.pojo.vo.save;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserVO {
    /**
     * 用户标识
     */
    private String guid;
    /**
     * 用户姓
     */
    private String firstName;

    /**
     * 用户名
     */
    private String lastName;

    /**
     * 登录名
     */
    private String logonName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态1可用0禁用
     */
    private Integer enabled;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 机构GUID
     */
    //todo
    private String orgGuid="todo";

    /**
     * 办公电话
     */
    private String officeTel;

    /**
     * 标记orgid隶属的类型：01-单位/部门，02-处室
     */
    private String orgType;

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
     *  人员ID
     */
    private String empGuid;

    private Integer version;
}
