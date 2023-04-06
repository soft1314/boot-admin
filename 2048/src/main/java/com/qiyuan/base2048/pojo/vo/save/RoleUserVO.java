package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleUserVO {
    @NotBlank(message = "用户GUID是必填项。")
    private String userGuid;
    @NotNull(message = "角色GUID是必填项。")
    private String roleGuid;
}
