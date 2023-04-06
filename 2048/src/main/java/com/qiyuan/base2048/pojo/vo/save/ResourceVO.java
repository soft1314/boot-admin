package com.qiyuan.base2048.pojo.vo.save;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-01 23:26
 **/
@Data
public class ResourceVO {
    /**
     * 主键GUID
     */
    private String guid;

    /**
     * 统一资源标志符
     */
    private String uri;

    /**
     * 资源分类 1菜单,2功能点
     */
    private String resourceType;

    /**
     * 资源访问控制类型01自由访问02登录访问03指定角色访问
     */
    private String accessControlStyle;

    /**
     * 备注
     */
    private String remarks;


    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源代码
     */
//    private String code;
    /**
     * 启用状态
     */
    private BigDecimal enabled;
    /**
     * 删除状态
     */
    private BigDecimal deleted;
    /**
     * 顺序号
     */
    private BigDecimal sort;
    /**
     * 父菜单的GUID
     */
    private String parentGuid;
    /**
     * 乐观锁
     */
    private BigDecimal version;
}
