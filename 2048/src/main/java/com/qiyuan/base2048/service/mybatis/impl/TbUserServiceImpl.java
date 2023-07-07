package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.mapper.mybatis.TbUserMapper;
import com.qiyuan.base2048.service.mybatis.ITbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统用户基础表 服务实现类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2021-11-09
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {
    /**
     * 按区划查询有效用户
     * @param admDivCode
     * @return
     * @throws Exception
     */
    @Override
    public List<TbUser> selectTbUserByDiv(String admDivCode) throws Exception{
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("ADM_DIV_CODE",admDivCode)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        List<TbUser> tbUserList = this.list(queryWrapper);
        return tbUserList;
    }
    /**
     * 查出所有有效用户
     * @return
     * @throws Exception
     */
    @Override
    public List<TbUser> selectAllTbUser() throws Exception{
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        List<TbUser> tbUserList = this.list(queryWrapper);
        return tbUserList;
    }
    /**
     * 按机构GUID查出所有有效用户
     * @param orgGuid
     * @return
     * @throws Exception
     */
    @Override
    public List<TbUser> selectTbUserByOrg(String orgGuid) throws Exception{
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("ORG_GUID",orgGuid)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        List<TbUser> tbUserList = this.list(queryWrapper);
        return tbUserList;
    }
    /**
     * 按人员用户关联，按通知到人员关联，取用户列表
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    @Override
    public List<TbUser> selectTbUserByNotifyGuidWithEmpRelation(String notifyGuid) throws Exception{
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("EMP_GUID"," SELECT EMP_GUID FROM tr_notify_emp WHERE notify_guid='"+notifyGuid+"' AND enabled='"+IsEnabledEnum.ENABLED.getStringValue()+"' AND deleted='"+IsDeletedEnum.NOTDELETED.getStringValue()+"'")
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        List<TbUser> tbUserList = this.list(queryWrapper);
        return tbUserList;
    }
}
