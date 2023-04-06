package com.qiyuan.bautil.dto.redis;

import com.qiyuan.bautil.dto.BaseUser;
import lombok.Data;

@Data
public class BaseUserDTO {
    private BaseUser baseUser;
    private String refreshToken;
    private String accessToken;
    private UserAgentDTO userAgentDTO;
}
