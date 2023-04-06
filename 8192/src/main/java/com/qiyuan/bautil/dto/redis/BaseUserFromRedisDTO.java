package com.qiyuan.bautil.dto.redis;

import com.qiyuan.bautil.dto.BaseUser;
import lombok.Data;

@Data
public class BaseUserFromRedisDTO {
    private BaseUser baseUser;
    private Boolean needRefreshToken;
}
