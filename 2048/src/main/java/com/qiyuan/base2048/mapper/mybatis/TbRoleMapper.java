package com.qiyuan.base2048.mapper.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbRole;
import com.qiyuan.bautil.dto.TransferItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2021-10-14
 */
public interface TbRoleMapper extends BaseMapper<TbRole> {
    /** role 使用code,可读性强一些 **/
    @Select("select code as \"key\",name as \"label\",0 as disabled from tb_role where enabled=#{enabled} and deleted=#{deleted} order by name")
    List<TransferItem> getAllEnabledRols(@Param("enabled") Integer enabled, @Param("deleted") Integer deleted) throws Exception;
}
