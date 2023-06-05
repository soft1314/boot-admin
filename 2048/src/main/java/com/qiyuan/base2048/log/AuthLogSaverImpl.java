package com.qiyuan.base2048.log;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogAuth;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.ResultTypeEnum;
import com.qiyuan.bautil.util.IpAddrUtil;
import com.qiyuan.bautil.util.ServletUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthLogSaverImpl implements AuthLogSaver{
    @Resource
    private AuthLogAsyncSaver authLogAsyncSaver;

    @Override
    public void saveLog(String userName, ResultTypeEnum resultType, String msg) {
        TbLogAuth tbLogAuth = new TbLogAuth();
        tbLogAuth.setUserName(userName);
        tbLogAuth.setOperIp(IpAddrUtil.getIpAddr(ServletUtil.getRequest()));
        tbLogAuth.setMsg(msg);
        tbLogAuth.setResultType(resultType.getValue());
        tbLogAuth.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        tbLogAuth.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        authLogAsyncSaver.asyncSave(tbLogAuth);
    }
}
