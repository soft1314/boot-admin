package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;
import com.qiyuan.bautil.dto.BasePageQueryVO;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectQueryVO extends BasePageQueryVO{
    /**
     * 主键GUID
     */
    private String guid;
    /**
     *  组织ID
     */

    private String groupId;

    /**
     *  项目ID
     */

    private String artifactId;

    /**
     *  项目名称
     */

    private String name;

    /**
     *  项目包名
     */

    private String packageProj;

    /**
     *  MP ENTITY包名
     */

    private String packageEntity;

    /**
     *  MP MAPPER类包名
     */

    private String packageMapper;

    /**
     *  MP SERVICE包名
     */

    private String packageService;
    /**
     *  SCENE SERVICE包名
     */
    private String packageServiceSc;
    /**
     *  MP CONTROLLER包名
     */

    private String packageController;
    /**
     *  SCENE CONTROLLER包名
     */
    private String packageControllerSc;

    /**
     *  VO包名
     */

    private String packageVo;
    /**
     *  MapStruct包名
     */

    private String packageMapstruct;

    /**
     *  项目JAVA文件路径
     */

    private String javaOutputDir;

    /**
     *  项目Mapper XML文件路径
     */

    private String xmlOutputDir;

    /**
     *  VUE文件路径
     */

    private String vueDir;

    /**
     *  API文件路径
     */

    private String apiJsDir;

    /**
     *  代码类型定义JS路径
     */

    private String dictypeJsDir;

    /**
     *  是否缺省项目
     */

    private String defaultProj;

    /**
     *  是否启用swagger
     */

    private String enableSwagger;

    /**
     *  作者
     */

    private String author;

    /**
     *  是否删除
     */

    private BigDecimal deleted;

    /**
     *  启用状态;ENABLED
     */

    private BigDecimal enabled;

    /**
     *  乐观锁
     */

    private BigDecimal version;

    /**
     *  创建时间
     */

    private Date createTime;

    /**
     *  创建人
     */

    private String createBy;

    /**
     *  更新时间
     */

    private Date modifyTime;

    /**
     *  更新人
     */

    private String modifyBy;
    /**
     *  CHANGE_LOG路径
     */
    private String changeLogDir;
}
