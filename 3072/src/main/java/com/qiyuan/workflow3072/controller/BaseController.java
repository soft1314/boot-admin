package com.qiyuan.workflow3072.controller;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.util.UserTool;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    public BaseUser getUser() {
        return UserTool.getBaseUser();
    }

    public String getUserId() {
        return getUser().getUserGuid();
    }

    public String getUsername() {
        return getUser().getLogonName();
    }
}
