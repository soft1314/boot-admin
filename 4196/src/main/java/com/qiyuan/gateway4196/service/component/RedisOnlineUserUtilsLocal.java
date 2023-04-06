package com.qiyuan.gateway4196.service.component;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.redis.BaseUserDTO;
import com.qiyuan.bautil.dto.redis.OnlineUserVO;
import com.qiyuan.bautil.dto.redis.UserAgentDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisOnlineUserUtilsLocal {
    @Resource
    private RedisCacheUtilsLocal redisCacheUtils;

    public static final String ONLINE_USER_KEY_PERFIX="sys_online_user_";
    public static final String ONLINE_ACCESS_TOKEN_PERFIX="sys_access_token_";
    public static final String ONLINE_REFRESH_TOKEN_PERFIX="sys_refresh_token_";
    /** 在线用户失效时间单位和数值 **/
    public static final TimeUnit ONLINE_USER_TIMEOUT_TIME_UNIT = TimeUnit.MINUTES;
    public static final Integer ONLINE_USER_TIMEOUT_VALUE = 240;  //240分钟，4小时
    public static final Integer ONLINE_REFRESS_TOKEN_TIMEOUT_VALUE = 240;  //240分钟，4小时
    public static final Integer ONLINE_ACCESS_TOKEN_TIMEOUT_VALUE = 60;  //暂定10分钟 开发时60
    /** 刷新令牌的幼年期限 秒，幼年不生产 **/
    public static final Integer ONLINE_REFRESS_TOKEN_CHILDHOOD_VALUE = 15;  //秒
    /** 对象允许弥留(死后驻留)秒数 **/
    public static final Integer ONLINE_OBJECT_DYING_VALUE = 15;  //秒
    /* 操作令牌剩余寿命低于此值，就需要运用刷新续命 ， */
    public static final Long TOKEN_REFRESH_SAFE_TIME = 1800L; //操作令牌生成1分钟以上就需要刷新 10分钟-1分钟=9分钟=540秒  开发时1800

    @Autowired
    public RedisTemplate redisTemplate;

    public void addUser(BaseUser baseUser, UserAgentDTO userAgentDTO) throws Exception{
        addUser(baseUser,"","",userAgentDTO);
    }
    public void addUser(BaseUser baseUser,String accressToken,UserAgentDTO userAgentDTO) throws Exception{
        addUser(baseUser,accressToken,"",userAgentDTO);
    }
    public void addUser(BaseUser baseUser,String accressToken,String refreshToken, UserAgentDTO userAgentDTO) throws Exception{
        BaseUserDTO baseUserDTO = new BaseUserDTO();
        baseUserDTO.setBaseUser(baseUser);
        baseUserDTO.setAccessToken(accressToken);
        baseUserDTO.setRefreshToken(refreshToken);
        baseUserDTO.setUserAgentDTO(userAgentDTO);
        redisCacheUtils.setCacheObject(this.getSysOnlineUserKeyForRedis(baseUser.getUserGuid()),baseUserDTO,ONLINE_USER_TIMEOUT_VALUE,ONLINE_USER_TIMEOUT_TIME_UNIT);
    }
    public void clear(String userGuid) throws Exception{
        String userKey = this.getSysOnlineUserKeyForRedis(userGuid);
        BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(userKey);
        redisCacheUtils.deleteObject(userKey);
        if(StringUtils.isNotBlank(baseUserDTO.getAccessToken())) {
            redisCacheUtils.deleteObject(this.getSysAccessTokenKeyForRedis(baseUserDTO.getAccessToken()));
        }
        if(StringUtils.isNotBlank(baseUserDTO.getRefreshToken())) {
            redisCacheUtils.deleteObject(this.getSysRefreshTokenKeyForRedis(baseUserDTO.getRefreshToken()));
        }
        /** 对于弥留期的令牌，无法定位，自生自灭吧 **/
    }

    /**
     * 适用登录时生成访问令牌
     * @param baseUser
     * @param accessToken
     * @throws Exception
     */
    public void addAccessToken(BaseUser baseUser, String accessToken, UserAgentDTO userAgentDTO) throws Exception{
        addUser(baseUser,accessToken,userAgentDTO);
        redisCacheUtils.setCacheObject(this.getSysAccessTokenKeyForRedis(accessToken),baseUser.getUserGuid(),ONLINE_ACCESS_TOKEN_TIMEOUT_VALUE,ONLINE_USER_TIMEOUT_TIME_UNIT);
    }

    /**
     * 适用getUserInfo时生成刷新令牌
     * @param baseUser
     * @param refreshToken
     * @throws Exception
     */
    public void addRefressToken(BaseUser baseUser,String refreshToken) throws Exception{
        BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(this.getSysOnlineUserKeyForRedis(baseUser.getUserGuid()));
        /** 重新放用户 **/
        baseUserDTO.setRefreshToken(refreshToken);
        baseUserDTO.getUserAgentDTO().setRefreshTime(new Date());
        redisCacheUtils.setCacheObject(this.getSysOnlineUserKeyForRedis(baseUser.getUserGuid()),baseUserDTO,ONLINE_USER_TIMEOUT_VALUE,ONLINE_USER_TIMEOUT_TIME_UNIT);
        /** 安放刷新令牌 **/
        redisCacheUtils.setCacheObject(this.getSysRefreshTokenKeyForRedis(refreshToken),baseUser.getUserGuid(),ONLINE_REFRESS_TOKEN_TIMEOUT_VALUE,ONLINE_USER_TIMEOUT_TIME_UNIT);
    }

    /**
     * 访问最频繁的,一般是来自网关的请求
     * 通过访问令牌，获取是否需要刷新一下令牌
     * @return
     * @throws Exception
     */
    public Boolean getAccessTokenRefreshNeed(String accessToken) throws Exception{
        String key = this.getSysAccessTokenKeyForRedis(accessToken);
        Long ttl = redisCacheUtils.getExpire(key);
        /**
         * 你剩余时间已经不多了
         */
        if(ttl.compareTo(TOKEN_REFRESH_SAFE_TIME) < 0 ){
            log.info("令牌需要刷新");
            return true;
        }
        return false;
    }
    public Boolean getAccessTokenRefreshNeed_old(String accessToken) throws Exception{
        String key = this.getSysAccessTokenKeyForRedis(accessToken);
        Long ttl = redisCacheUtils.getExpire(key);
        /**
         * 剩余时间少，但不能太少（太少的情况，可能是主动设置的）
         */
        if(ttl.compareTo(TOKEN_REFRESH_SAFE_TIME) < 0 && ttl.compareTo(10L) > 0){
            return true;
        }
        return false;
    }
    /**
     * 访问最频繁的,一般是来自网关的请求
     * 通过访问令牌，获取用户
     * @return
     * @throws Exception
     */
    public BaseUser getBaseUserByAccessToken(String accessToken) throws Exception{
        String key = this.getSysAccessTokenKeyForRedis(accessToken);
        String userGuid = redisCacheUtils.getCacheObject(key);
        if(StringUtils.isBlank(userGuid)){
            throw new Exception("令牌丢失了。");
        }
        BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(this.getSysOnlineUserKeyForRedis(userGuid));
        if(baseUserDTO == null){
            throw new Exception("令牌丢失了。");
        }
        return baseUserDTO.getBaseUser();
    }

    /**
     * 刷新令牌
     * 幼年不刷新，老去有弥留
     * @param refreshToken
     * @return
     * @throws Exception
     */
    public synchronized BaseUserDTO createAccessTokenByRefreshToken(String refreshToken) throws Exception{
        String refreshTokenKey = this.getSysRefreshTokenKeyForRedis(refreshToken);
        Long refreshTokenTtl = redisCacheUtils.getExpire(refreshTokenKey);
        Long refreshTokenInitTtl = ONLINE_REFRESS_TOKEN_TIMEOUT_VALUE * 60L;
        /** 初始寿命 - 剩余寿命 = 秒龄 < 幼年期限,查出数据返回，不生产 **/
        if ((refreshTokenInitTtl - refreshTokenTtl) < ONLINE_REFRESS_TOKEN_CHILDHOOD_VALUE) {
            String userGuid = redisCacheUtils.getCacheObject(refreshTokenKey);
            if (StringUtils.isBlank(userGuid)) {
                throw new Exception("令牌已过期，暂不实现重新从数据库装入的功能。");
            }
            String userTokenKey = this.getSysOnlineUserKeyForRedis(userGuid);
            BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(userTokenKey);
            if (baseUserDTO == null) {
                throw new Exception("令牌已过期。");
            }
            return baseUserDTO;
        }
        /** 成年的刷新令牌，可以刷新 **/
        String userGuid = redisCacheUtils.getCacheObject(refreshTokenKey);
        if (StringUtils.isBlank(userGuid)) {
            throw new Exception("令牌已过期，暂不实现重新从数据库装入的功能。");
        }
        String userTokenKey = this.getSysOnlineUserKeyForRedis(userGuid);
        BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(userTokenKey);
        if (baseUserDTO == null) {
            throw new Exception("令牌已过期。");
        }
        /** 记录旧 **/
        String oldAccToken = baseUserDTO.getAccessToken();
        String oldRefToken = baseUserDTO.getRefreshToken();
        /** 生成一对新的 **/
        String newAccessToken = java.util.UUID.randomUUID().toString();
        String newRefreshToken = java.util.UUID.randomUUID().toString();
        /** 安放用户，因为原值要弥留，所以放新对象 **/
        baseUserDTO.setAccessToken(newAccessToken);
        baseUserDTO.setRefreshToken(newRefreshToken);
        baseUserDTO.getUserAgentDTO().setRefreshTime(new Date());
        redisCacheUtils.setCacheObject(this.getSysOnlineUserKeyForRedis(userGuid),baseUserDTO,ONLINE_USER_TIMEOUT_VALUE,ONLINE_USER_TIMEOUT_TIME_UNIT);
        /** 安放新操作令牌 **/
        redisCacheUtils.setCacheObject(this.getSysAccessTokenKeyForRedis(newAccessToken), userGuid, ONLINE_ACCESS_TOKEN_TIMEOUT_VALUE, ONLINE_USER_TIMEOUT_TIME_UNIT);
        /** 安放新刷新刷新令牌 **/
        redisCacheUtils.setCacheObject(this.getSysRefreshTokenKeyForRedis(newRefreshToken), userGuid, ONLINE_REFRESS_TOKEN_TIMEOUT_VALUE, ONLINE_USER_TIMEOUT_TIME_UNIT);

        /** 留15秒,前端异步并发可能还使用原token **/
        redisCacheUtils.expire(this.getSysAccessTokenKeyForRedis(oldAccToken), ONLINE_OBJECT_DYING_VALUE, TimeUnit.SECONDS);
        redisCacheUtils.expire(this.getSysRefreshTokenKeyForRedis(oldRefToken), ONLINE_OBJECT_DYING_VALUE, TimeUnit.SECONDS);

        baseUserDTO.setRefreshToken(newRefreshToken);
        baseUserDTO.setAccessToken(newAccessToken);
        return baseUserDTO;
    }
    public synchronized BaseUserDTO createAccessTokenByRefreshToken_old(String refreshToken) throws Exception{
        String key = this.getSysRefreshTokenKeyForRedis(refreshToken);
        String userGuid = redisCacheUtils.getCacheObject(key);
        if (StringUtils.isBlank(userGuid)) {
            throw new Exception("令牌已过期，暂不实现重新从数据库装入的功能。");
        }

        String userKey = this.getSysOnlineUserKeyForRedis(userGuid);
        /* 看时间 20S以上 */
        BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(userKey);
        Long src = ONLINE_USER_TIMEOUT_VALUE * 60L;
        Long ttl = redisCacheUtils.getExpire(userKey);
        if ((src - ttl) < 30) {
            return baseUserDTO;
        }

        if (baseUserDTO == null) {
            throw new Exception("令牌已过期。");
        }
        String oldAccToken = baseUserDTO.getAccessToken();
        String oldRefToken = baseUserDTO.getRefreshToken();
        String newAccessToken = java.util.UUID.randomUUID().toString();
        String newRefreshToken = java.util.UUID.randomUUID().toString();
        addUser(baseUserDTO.getBaseUser(), newAccessToken, newRefreshToken, baseUserDTO.getUserAgentDTO());
        redisCacheUtils.setCacheObject(this.getSysAccessTokenKeyForRedis(newAccessToken), baseUserDTO.getBaseUser().getUserGuid(), ONLINE_ACCESS_TOKEN_TIMEOUT_VALUE, ONLINE_USER_TIMEOUT_TIME_UNIT);

        addRefressToken(baseUserDTO.getBaseUser(), newRefreshToken);
        /** 留15秒,前端异步并发可能还使用原token **/
        redisCacheUtils.expire(this.getSysAccessTokenKeyForRedis(oldAccToken), 15, TimeUnit.SECONDS);
        redisCacheUtils.expire(this.getSysRefreshTokenKeyForRedis(oldRefToken), 15, TimeUnit.SECONDS);

        baseUserDTO.setRefreshToken(newRefreshToken);
        baseUserDTO.setAccessToken(newAccessToken);
        return baseUserDTO;
    }

    /**
     * 查找匹配key
     * @param pattern key
     * @return /
     */
    public List<String> findKeys(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 分页查询 key
     * @param patternKey key
     * @param page 页码
     * @param size 每页数目
     * @return /
     */
    public List<String> findKeysForPage(String patternKey, int page, int size) {
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>(size);
        int tmpIndex = 0;
        int fromIndex = page * size;
        int toIndex = page * size + size;
        while (cursor.hasNext()) {
            if (tmpIndex >= fromIndex && tmpIndex < toIndex) {
                result.add(new String(cursor.next()));
                tmpIndex++;
                continue;
            }
            // 获取到满足条件的数据后,就可以退出了
            if (tmpIndex >= toIndex) {
                break;
            }
            tmpIndex++;
            cursor.next();
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<OnlineUserVO> getOnlineUserPage(int page, int size) throws Exception{
        List<OnlineUserVO> onlineUserVOS = new ArrayList<>();
        String patternKey = ONLINE_USER_KEY_PERFIX+"*";
        List<String> userKeys = this.findKeysForPage(patternKey,page,size);
        for(String key:userKeys){
            BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(key);
            if(baseUserDTO == null){
                continue;
            }
            OnlineUserVO onlineUserVO = new OnlineUserVO();
            onlineUserVO.setBrowser(baseUserDTO.getUserAgentDTO().getBrowser());
            onlineUserVO.setIpAddr(baseUserDTO.getUserAgentDTO().getIpAddr());
            onlineUserVO.setLogonName(baseUserDTO.getBaseUser().getLogonName());
            onlineUserVO.setLogonTime(baseUserDTO.getUserAgentDTO().getUpTime());
            onlineUserVO.setRefreshTime(baseUserDTO.getUserAgentDTO().getRefreshTime());
            onlineUserVO.setOs(baseUserDTO.getUserAgentDTO().getOs());

            onlineUserVOS.add(onlineUserVO);
        }
        return onlineUserVOS;
    }
    public Boolean isRefreshTokenNull(String userGuid) throws Exception{
        String key = this.getSysOnlineUserKeyForRedis(userGuid);
        BaseUserDTO baseUserDTO = redisCacheUtils.getCacheObject(key);
        if(baseUserDTO == null){
            throw new Exception("丢失");
        }
        return StringUtils.isBlank(baseUserDTO.getRefreshToken());
    }
    private String getSysOnlineUserKeyForRedis(String userGuid){
        return ONLINE_USER_KEY_PERFIX + userGuid;
    }
    private String getSysAccessTokenKeyForRedis(String accessToken){
        return ONLINE_ACCESS_TOKEN_PERFIX + accessToken;
    }
    private String getSysRefreshTokenKeyForRedis(String refreshToken){
        return ONLINE_REFRESH_TOKEN_PERFIX + refreshToken;
    }

}



