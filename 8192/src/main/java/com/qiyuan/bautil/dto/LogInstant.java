package com.qiyuan.bautil.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LogInstant {
    private BigDecimal epochSecond;
    private BigDecimal nanoOfSecond;
}
