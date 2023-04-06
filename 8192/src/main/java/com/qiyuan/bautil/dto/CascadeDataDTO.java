package com.qiyuan.bautil.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020/11/5 23:32
 **/
@Data
public class CascadeDataDTO {
    private String value;
    private String label;
    private List<CascadeDataDTO> children;
}
