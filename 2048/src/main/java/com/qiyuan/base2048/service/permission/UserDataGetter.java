package com.qiyuan.base2048.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.query.UserPageQueryVO;

public interface UserDataGetter {
    IPage<TbUser> getPage(UserPageQueryVO userPageQueryVO) throws Exception;
}
