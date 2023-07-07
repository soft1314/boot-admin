package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import java.util.List;

@Data
public class CascaderOptionsVO {
    private String value;
    private String label;
    List<CascaderOptionsVO> children;
}
