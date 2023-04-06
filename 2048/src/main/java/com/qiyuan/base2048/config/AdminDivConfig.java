package com.qiyuan.base2048.config;

import com.qiyuan.bautil.dto.CascaderNode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

//@Data
//@Component
//@ConfigurationProperties("base2048.default.admin-div")
public class AdminDivConfig {
    private List<String> value;
    private List<CascaderNode> options;
}