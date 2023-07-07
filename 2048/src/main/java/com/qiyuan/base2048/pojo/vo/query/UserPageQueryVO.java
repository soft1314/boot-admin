package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;
import com.qiyuan.bautil.dto.BasePageQueryVO;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-09 21:43
 **/
@Data
public class UserPageQueryVO  extends BasePageQueryVO{
    private String logonName;
    private String roleId;
    private String firstName;
    private String lastName;
    private String orgType;
    private Integer enabled;
    private Integer deleted;
}
