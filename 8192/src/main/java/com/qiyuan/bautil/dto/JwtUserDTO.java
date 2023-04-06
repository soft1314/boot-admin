package com.qiyuan.bautil.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-17 11:16
 **/
@Data
public class JwtUserDTO {
    /**
     * 用户在平台库的id
     */
    private String userId;
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
    private Long orgId;
    /**
     *用户在平台库中的区划编码（9位）
     */
    private String mofDivCode;
    /**
     *用户角色id的list
     */
    private List<String> userRoles;
    /**
     *用户在政采库中的用户类型
     */
    private BigDecimal gpOrgType;
    /**
     *用户在政采库中的所属单位或处室id
     */
    private Long gpOrgId;
    /**
     * 用户在政采库中的区划编码（6位）
     */
    private String gpMofDivCode;
    /**
     *用户政采模块的角色编码列表
     */
    private List<String> userGpRoleCodes;
}
