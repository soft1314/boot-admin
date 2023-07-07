package com.qiyuan.base2048.quartz.dto;

import lombok.Data;

@Data
public class JobUpdateDTO  extends JobDTO{
    private String oldJobName;
    private String oldJobGroup;
}
