package com.qiyuan.base2048.pojo.dto.freemarker;

import com.qiyuan.base2048.pojo.vo.vuecomponent.TableColVO;
import lombok.Data;

import java.util.List;

@Data
public class VueMarker {
    /** 作者 **/
    private String author;
    /** api js文件名 ,表名小写减号连接 **/
    private String apiJsFileName;
    /** 实体类名 首字母大写驼峰 **/
    private String entityName;
    /** 列 **/
    List<TableColVO> cols;
}
