package com.qiyuan.base2048.mapper.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleResource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 角色资源关系表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface TrRoleResourceMapper extends BaseMapper<TrRoleResource> {
    @Select("select a.role_guid from tr_role_resource a,tb_resource b where b.uri=#{uri} and a.resource_guid=b.guid and a.enabled=#{enabled} and a.deleted=#{deleted} and b.enabled=#{enabled} and b.deleted=#{deleted}")
    List<String> selectRoleListByResourceUri(@Param("uri") String uri, @Param("enabled") String enabled, @Param("deleted") String deleted) throws Exception;

    @Select("select role_guid from tr_role_resource where resource_guid=#{resourceGuid} and enabled=#{enabled} and deleted=#{deleted}")
    List<String> selectResourceRoleListByResourceGuid(@Param("resourceGuid") String resourceGuid,@Param("enabled") String enabled,@Param("deleted") String deleted) throws Exception;

}