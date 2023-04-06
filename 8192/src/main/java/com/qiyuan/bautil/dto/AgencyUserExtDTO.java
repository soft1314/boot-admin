package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author 虚领顶劲气沉丹田
 * @create 2021/1/13 8:49
 **/
@Data
public class AgencyUserExtDTO {
    @NotBlank(message = "未获取预算单位用户主键")
    private String agencyUserId;
    @NotBlank(message = "未获取预算单位联系人姓名")
    private String agencyLinkMan;
    @NotBlank(message = "未获取预算单位联系人电话")
    private String agencyTel;
}
