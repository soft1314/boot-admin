package com.qiyuan.bautil.dto;

import lombok.Data;

import java.util.List;

@Data
public class CascaderDataVO {
    private List<String> value;
    private String leafLabel;
}
