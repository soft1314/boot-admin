package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-07 22:56
 **/
@Data
public class ResourceRoleVO {
    @NotBlank(message = "资源GUID是必填项。")
    private String resourceGuid;
    @NotNull(message = "角色列表不允许为NULL。")
    private List<String> roleGuids;
}
