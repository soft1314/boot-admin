package com.qiyuan.bautil.dto.redis;

import lombok.Data;

import java.util.Date;

@Data
public class OnlineUserVO {
    private String logonName;
    private String ipAddr;
    private String browser;
    private String os;
    private Date logonTime;
    private Date refreshTime;
}
