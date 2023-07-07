package com.qiyuan.base2048.pojo.dto.mybatis;

import lombok.Data;

@Data
public class ServiceDTO extends MapperDTO{
    /** Mapper类包全名 **/
    private String mapperPackageUrl;
}
