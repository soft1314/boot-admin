package com.qiyuan.bautil.dto.redis;

import lombok.Data;

@Data
public class JsonEncStringDTO {
    private String jsonEncString;
    private Boolean needRefreshToken;
}
