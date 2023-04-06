package com.qiyuan.base2048.service.mybatis.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.mapper.mybatis.TbUserMapper;
import com.qiyuan.base2048.service.mybatis.ITbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
