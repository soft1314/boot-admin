package com.qiyuan.bautil.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-14 21:06
 **/
@Data
public class BaseUser {
    /**
     * 用户guid
     */
    private String userGuid;
    /**
     *用户在平台库的显示名
     */
    private String userName;
    /**
     *用户在平台库的登录名
     */
    private String logonName;
    /**
     *用户在平台库中的用户类型
     */
    private String orgType;
    /**
     *用户在平台库中的所属单位或处室id
     */
    private String orgGuid;
    /**
     *用户角色id的list
     */
    private List<String> userRoles;
    /**
     * 区划编码 todo
     */
    private String admDivCode = "370200000";
    /**
     * 所属单位的编码
     */
    private String orgCode="101001008";
    /**
     * 租户ID
     */
    private String tenantId;

}
