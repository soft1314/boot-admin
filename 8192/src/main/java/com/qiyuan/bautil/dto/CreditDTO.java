package com.qiyuan.bautil.dto;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @date 2020-11-30 15:04
 * @Description:
 */
@Data
public class CreditDTO {
    private static final long serialVersionUID=1L;
    private String id;
    private Integer roleType;
    private String roleName;
    private String roleCode;
    private String dishonestyCode;
    private String dishonestyName;
    private String dishonestyValue;
    private String accessoryCaskName;
    private String accessoryName;
    private String accessoryOldName;
    private String recordAgencyCode;
    private String asterisk;
    private Integer score;
    private String operatorId;
    private String operatorCode;
    private String state;
}
