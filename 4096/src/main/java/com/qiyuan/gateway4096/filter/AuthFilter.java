package com.qiyuan.gateway4096.filter;

import com.qiyuan.bautil.annotate.response.enumer.ResponseStatus;
import com.qiyuan.bautil.annotate.response.pojo.ControllerResultDTO;
import com.qiyuan.bautil.annotate.response.pojo.ResponseException;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.bautil.enums.TokenModeEnum;
import com.qiyuan.bautil.util.*;
import com.qiyuan.gateway4096.cache.ResourceCacheService;
import com.qiyuan.gateway4096.service.MenuPermissionChecker;
import com.qiyuan.gateway4096.service.component.RedisOnlineUserUtilsLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-25 17:41
 **/
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    private MenuPermissionChecker menuPermissionChecker;
    @Resource
    private ResourceCacheService resourceCacheService;
    @Resource
    private RedisOnlineUserUtilsLocal redisOnlineUserUtils;

    /** jwt or redis **/
    @Value("${gateway4096.token.mode}")
    private String tokenMode;
    /**
     * 执行过滤器中的业务逻辑
     * 根据令牌以及拟访问的资源RUI，进行权限检查
     * 自由访问的，放过
     * 登录访问的，有令牌，解析令牌，生成用户信息对象JSON放在Header里，放过；无令牌或者令牌失效，拒绝
     * 授权访问的，有令牌，解析令牌，生成用户信息对象JSON放在Header里，检查角色是否有权限，有权限放过；无权限，拒绝
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        String uri = exchange.getRequest().getPath().toString();
        log.info(uri);
        if(UrlTool.isFreeUrl(uri)){
            return chain.filter(exchange);
        }
        try {
            MenuDTO menuDTO = resourceCacheService.fetchMenuDtoByUri(uri);
            if (menuPermissionChecker.isFreeAccess(menuDTO)) {
                return chain.filter(exchange);
            }
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String token = headers.getFirst(JwtUtil.AUTHORIZE_TOKEN);
            log.info(token);

            if (StringUtils.isBlank(token)) {
                log.warn("token is blank ------令牌是空白");
                log.warn(exchange.getRequest().getMethodValue());
                ServerHttpResponse response = exchange.getResponse();
                String exceptjson = generateUnAuthorizedExcpetJoson(uri);
                byte[] bits = exceptjson.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }
            String jsonEncString = this.getJsonEncString(token.replace(JwtUtil.TOKEN_PREFIX,""));
            if(StringUtils.isBlank(jsonEncString)){
                log.warn("token is blank ------令牌是空白");
                ServerHttpResponse response = exchange.getResponse();
                String exceptjson = generateUnAuthorizedExcpetJoson(uri);
                byte[] bits = exceptjson.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }
            if (menuPermissionChecker.isAllRoleAccess(menuDTO)) {
                /** 资源对持任意令牌者都自由访问 **/
                Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                    httpHeader.set(JwtUtil.AUTHORIZE_USER, jsonEncString);
                };
                ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
                exchange.mutate().request(serverHttpRequest).build();
                return chain.filter(exchange);
            }
            List<String> functionRoles = resourceCacheService.fetchRoleListByUri(uri);
            String jsonString = Sm4EncryptUtil.decrypt(jsonEncString, JwtUtil.JWT_SM4_PADDING,JwtUtil.JWT_SM4_KEY);

            BaseUser baseUser = FastjsonUtil.getObject(jsonString, BaseUser.class);
            if (!RoleTool.roleCheck(baseUser.getUserRoles(), functionRoles)) {
                /** 资源需要指定角色访问，未通过 **/
                log.warn("Unauthorized access,uri={}------- 未通过权限检查", uri);
                ServerHttpResponse response = exchange.getResponse();
                String exceptjson = generateUnAuthorizedExcpetJoson(uri);
                byte[] bits = exceptjson.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }
            /** 权限检查已通过，给微服务提供用于获取用户信息的串 **/
            Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                httpHeader.set(JwtUtil.AUTHORIZE_USER, jsonEncString);
            };
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
            exchange.mutate().request(serverHttpRequest).build();
            //5.如果存在,继续执行
            return chain.filter(exchange); //继续向下执行

        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            ServerHttpResponse response = exchange.getResponse();
            String exceptjson = generateUnAuthorizedExcpetJoson(uri);
            byte[] bits = exceptjson.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
    }

    /**
     * 指定过滤器的执行顺序 , 返回值越小,执行优先级越高
     */
    @Override
    public int getOrder() {
        return 1;
    }

    private String generateUnAuthorizedExcpetJoson(String uri) {
        ResponseException ex = new ResponseException();
        ex.setResponseStatus(ResponseStatus.UNAUTHORIZED);
        ControllerResultDTO controllerResultDTO = ControllerResultDTO.failure(uri, ex,"false");
        return FastjsonUtil.getJson(controllerResultDTO);
    }
    private String getJsonEncString(String accessToken) throws Exception{
        if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {
            return JwtUtil.getUserInfoFromToken(accessToken);
        }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)){
            BaseUser baseUser = redisOnlineUserUtils.getBaseUserByAccessToken(accessToken);
            if(baseUser == null){
                throw new JwtUtil.TokenValidationException("令牌丢失了。");
            }
            String jsonString = FastjsonUtil.getJson(baseUser);
            String jsonEncString = Sm4EncryptUtil.encrypt(jsonString, JwtUtil.JWT_SM4_PADDING,JwtUtil.JWT_SM4_KEY);
            return jsonEncString;
        }else{
            throw new JwtUtil.TokenValidationException("令牌丢失了。");
        }

    }

}

