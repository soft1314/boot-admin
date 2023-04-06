package com.qiyuan.bautil.dto.redis;

import lombok.Data;

import java.util.Date;

@Data
public class UserAgentDTO {
    private String ipAddr;
    private String browser;
    private String os;
    private Date upTime;
    private Date refreshTime;
}
