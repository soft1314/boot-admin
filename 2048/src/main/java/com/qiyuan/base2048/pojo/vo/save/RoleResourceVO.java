package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleResourceVO {
    @NotBlank(message = "资源GUID是必填项。")
    private String resourceGuid;
    @NotNull(message = "角色GUID是必填项。")
    private String roleGuid;
}
