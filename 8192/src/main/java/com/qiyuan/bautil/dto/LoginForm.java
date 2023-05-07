package com.qiyuan.bautil.dto;

import com.qiyuan.bautil.annotate.valid.OverallValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-14 21:41
 **/
@Data
@OverallValid(value = "selfCheck1",message = "密码复杂度不够。")
public class LoginForm {
    @NotBlank(message = "登录账号是必填项。")
    private String username;
    @NotBlank(message = "登录密码是必填项。")
    private String password;
    @NotBlank(message = "验证码是必填项。")
    private String captchaVerification;
    /**
     * 租房ID
     */
    private String tenantId;
    public boolean selfCheck1(){
        /** todo 密码复杂度检查  **/
        /** todo 解密再检查强度 **/
        return true;
    }
}
