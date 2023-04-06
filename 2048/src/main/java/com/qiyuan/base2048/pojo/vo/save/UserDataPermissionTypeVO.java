package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDataPermissionTypeVO {
    @NotBlank(message = "userGuid是必填项。")
    private String userGuid;
    @NotBlank(message = "permissionType是必填项。")
    private String permissionType;
}
