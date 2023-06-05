package com.qiyuan.base2048.mapper.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 角色用户关联表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface TrRoleUserMapper extends BaseMapper<TrRoleUser> {
    /**
     * 使用USERGUID查询该用户所有角色
     * @param userGuid
     * @return
     * @throws Exception
     */
    @Select("select role_guid from tr_role_user where user_guid = #{userGuid} and enabled=#{enabled} and deleted=#{deleted}")
    List<String> selectRoleGuidListByUserGuid(@Param("userGuid") String userGuid, @Param("enabled") String enabled, @Param("deleted") String deleted) throws Exception;

}