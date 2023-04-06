package com.qiyuan.base2048.service.manage.impl;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import com.qiyuan.base2048.service.manage.OnlineUserService;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.redis.OnlineUserVO;
import com.qiyuan.bautil.service.component.RedisOnlineUserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OnlineUserServiceImpl implements OnlineUserService {
    @Resource
    private RedisOnlineUserUtils redisOnlineUserUtils;

    @Override
    public ResultDTO pageOnlineUserVO(BasePageQueryVO basePageQueryVO) throws Exception {
        List<OnlineUserVO> onlineUserVOList =  redisOnlineUserUtils.getOnlineUserPage(basePageQueryVO.getCurrentPage()-1,basePageQueryVO.getPageSize());
        return ResultDTO.success(onlineUserVOList);
    }

    @Override
    public ResultDTO listOnlineUserVO() throws Exception {

        return null;
    }
}
