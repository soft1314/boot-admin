package com.qiyuan.base2048.pojo.vo.query;

import com.qiyuan.bautil.dto.PageQueryDTO;
import lombok.Data;

@Data
public class RolePermissionQueryVO extends PageQueryDTO {
    private String roleGuid;
    /** 用户登录名 或 资源类别 **/
    private String code;
    /** 用户姓名 或 资源名称 **/
    private String name;
    /** 用户别名 或 资源定位 **/
    private String lastName;
}
