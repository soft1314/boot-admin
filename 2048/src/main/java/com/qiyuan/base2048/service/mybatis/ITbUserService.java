package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户基础表 服务类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2021-11-09
 */
public interface ITbUserService extends IService<TbUser> {
    /**
     * 按区划查询有效用户
     * @param admDivCode
     * @return
     * @throws Exception
     */
    List<TbUser> selectTbUserByDiv(String admDivCode) throws Exception;
    /**
     * 查出所有有效用户
     * @return
     * @throws Exception
     */
    List<TbUser> selectAllTbUser() throws Exception;
    /**
     * 按机构GUID查出所有有效用户
     * @param orgGuid
     * @return
     * @throws Exception
     */
    List<TbUser> selectTbUserByOrg(String orgGuid) throws Exception;
    /**
     * 按人员用户关联，按通知到人员关联，取用户列表
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    List<TbUser> selectTbUserByNotifyGuidWithEmpRelation(String notifyGuid) throws Exception;
}
