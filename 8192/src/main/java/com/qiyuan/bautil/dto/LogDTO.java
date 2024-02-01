package com.qiyuan.bautil.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LogDTO {
    private String thread;
    private String level;
    private String loggerName;
    private String message;
    private Object thrown;
    private Boolean endOfBatch;
    private String loggerFqcn;
    private BigDecimal threadId;
    private BigDecimal threadPriority;
    private LogInstant instant;
    private KeyValuePair[] contextMap;
    private LogSource source;

    private String hostName;
    private String timeStamp;
}
