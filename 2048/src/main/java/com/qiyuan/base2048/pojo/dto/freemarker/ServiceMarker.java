package com.qiyuan.base2048.pojo.dto.freemarker;

import com.qiyuan.base2048.pojo.vo.vuecomponent.TableColVO;
import lombok.Data;

import java.util.List;

@Data
public class ServiceMarker {
    /** 表描述 **/
    private String tableComment;
    /** 包全名 **/
    private String packageUrl;
    /** 作者 **/
    private String author;
    /** 实体类名 首字母大写驼峰 **/
    private String entityName;
    /** 实体类包全名 **/
    private String entityPackageUrl;
    /** VO类包全名 **/
    private String voPackageUrl;
    /** Mapstruct 类包全名 **/
    private String mapstructPackageUrl;
    /** Mapper包全名 **/
    private String mapperPackageUrl;
    /** 列 **/
    List<TableColVO> cols;
}
