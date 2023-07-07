package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserEmpVO {
    @NotBlank(message = "用户ID是必填项。")
    private String userGuid;
    @NotBlank(message = "用户登录名是必填项。")
    private String logonName;
    @NotBlank(message = "人员ID是必填项。")
    private String empGuid;
    @NotBlank(message = "人员姓名是必填项。")
    private String empName;
    @NotBlank(message = "人员区划编码是必填项。")
    private String admDivCode;
    @NotBlank(message = "人员区划名称是必填项。")
    private String admDivName;
    @NotBlank(message = "人员机构编码是必填项。")
    private String orgCode;
    @NotBlank(message = "人员机构名称是必填项。")
    private String orgName;
    @NotNull(message = "乐观锁是必填项。")
    private Integer version;
}
