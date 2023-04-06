package com.qiyuan.bautil.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-23 14:16
 **/
@Data
public class TransferDataDTO {
    List<TransferItem> allData;
    List<String> hasData;
}
