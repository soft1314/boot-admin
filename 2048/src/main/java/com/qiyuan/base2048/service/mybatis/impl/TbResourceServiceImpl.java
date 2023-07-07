package com.qiyuan.base2048.service.mybatis.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.mapper.mybatis.TbResourceMapper;
import com.qiyuan.base2048.service.mybatis.ITbResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统资源（功能、菜单）基础表 服务实现类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2021-11-06
 */
@Service
public class TbResourceServiceImpl extends ServiceImpl<TbResourceMapper, TbResource> implements ITbResourceService {

}
