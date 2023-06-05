package com.qiyuan.bautil.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LogSource {
    @JsonProperty(value = "class")
    private String className;
    private String method;
    private String file;
    private BigDecimal line;
}
