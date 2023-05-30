package com.qiyuan.bautil.util;

import com.qiyuan.bautil.dto.BaseUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {
    /** 令牌过期时间：1天  */
//    private static final long JWT_EXPIRATION_TIME = 86400_000;
    /** 令牌过期时间：30 小时 */
    private static final long JWT_EXPIRATION_TIME = 108000_000;
    /** 令牌过期时间：30分钟  */
//    private static final long JWT_EXPIRATION_TIME = 1800_000;

    private static final String JWT_SECRET = "ThisIsASecret";
    private static final String JWT_KEY = "USER";
    public static final String TOKEN_PREFIX = "Bearer";
    /** 前端放token的KEY **/
    public static final String AUTHORIZE_TOKEN = "X-Token";
    /** 网关放是否超时，需要刷新令牌的标志 **/
    public static final String AUTHORIZE_TOKEN_TIMEOUT = "X-Token-Timeout";
    /** 经过网关后，用户信息JSON加密串在header中的key **/
    public static final String AUTHORIZE_USER = "X-Gp-User";
//    public static final String JWT_AES_KEY="abcdefghij123456";
    public static final String JWT_AES_KEY="qazxswedcrfvbgt0";

    public static final String JWT_SM4_KEY="abcdefghij123456";
    public static final String JWT_SM4_PADDING="qazxswedcrfvbgt0";

//    public static String generateToken(JwtUserDTO jwtUserDTO) throws Exception {
//        if (jwtUserDTO == null){
//            throw new Exception("制作令牌参数为NULL。");
//        }
//        String jsonString = FastjsonUtil.getJson(jwtUserDTO);
//        String jsonEncString = AesUtil.encrypt(jsonString, JWT_AES_KEY);
//        HashMap<String, Object> map = new HashMap<>(1);
//        map.put(JWT_KEY, jsonEncString);
//        String jwt = Jwts.builder()
//                .setClaims(map)
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//        return jwt;
//    }
//    public static JwtUserDTO validateToken(String token) throws Exception{
//        if (StringUtils.isBlank(token)) {
//            log.warn("令牌丢失了。");
//            throw new TokenValidationException("令牌丢失了。");
//        }
//        Map<String, Object> body = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                .getBody();
//        String jsonEncString = (String) body.get(JWT_KEY);
//        String jsonString = AesUtil.decrypt(jsonEncString, JWT_AES_KEY);
//        JwtUserDTO jwtUserDTO = (JwtUserDTO) FastjsonUtil.getObject(jsonString, JwtUserDTO.class);
//        return jwtUserDTO;
//    }
    public static String getUserInfoFromToken(String token) throws Exception{
        if (StringUtil.isBlank(token)) {
            log.warn("令牌丢失了。");
            throw new TokenValidationException("令牌丢失了。");
        }

        Map<String, Object> body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        String jsonEncString = (String) body.get(JWT_KEY);
        return jsonEncString;
    }
    public static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }

    public static String generateToken(BaseUser baseUser) throws Exception {
        if (baseUser == null){
            throw new Exception("制作令牌参数为NULL。");
        }
        String jsonString = FastjsonUtil.getJson(baseUser);
        String jsonEncString = Sm4EncryptUtil.encrypt(jsonString, JWT_SM4_PADDING,JWT_SM4_KEY);
        HashMap<String, Object> map = new HashMap<>(1);
        map.put(JWT_KEY, jsonEncString);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return jwt;
    }
    public static <T> T fetchDtoFromToken(String token,Class<T> tclass) throws Exception{
        if (StringUtil.isBlank(token)) {
            log.warn("令牌丢失了。");
            throw new TokenValidationException("令牌丢失了。");
        }
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        String jsonEncString = (String) body.get(JWT_KEY);
        String jsonString = Sm4EncryptUtil.decrypt(jsonEncString, JWT_SM4_PADDING,JWT_SM4_KEY);
        T dto = (T)FastjsonUtil.getObject(jsonString, tclass);
        return dto;
    }
}

