package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRoleVO {
    @NotBlank(message = "用户GUID是必填项。")
    private String userGuid;
    @NotNull(message = "角色列表不允许为NULL。")
    private List<String> userRoleGuids;
}
