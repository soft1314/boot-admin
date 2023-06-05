package com.qiyuan.gateway4096.filter;

import com.qiyuan.bautil.annotate.response.enumer.ResponseStatus;
import com.qiyuan.bautil.annotate.response.pojo.ControllerResultDTO;
import com.qiyuan.bautil.annotate.response.pojo.ResponseException;
import com.qiyuan.bautil.enums.TokenModeEnum;
import com.qiyuan.bautil.util.FastjsonUtil;
import com.qiyuan.bautil.util.JwtUtil;
import com.qiyuan.bautil.util.UrlTool;
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
import java.util.function.Consumer;

@Component
@Slf4j
public class TokenTimeOutFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisOnlineUserUtilsLocal redisOnlineUserUtils;
    /** jwt or redis **/
    @Value("${gateway4096.token.mode}")
    private String tokenMode;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getPath().toString();
        log.info(uri);
        if(UrlTool.isFreeUrl(uri)){
            return chain.filter(exchange);
        }
        Boolean needRefresh ;
        try {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String token = headers.getFirst(JwtUtil.AUTHORIZE_TOKEN);
            if(StringUtils.isBlank(token)){
                needRefresh = false;
            }else {
                needRefresh = this.needRefreshToken(token.replace(JwtUtil.TOKEN_PREFIX, ""));
            }
            /** 判断是否需要刷新令牌了，如果是，设置TRUE标志，如果不需要，设置FALSE**/
            Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                httpHeader.set(JwtUtil.AUTHORIZE_TOKEN_TIMEOUT, needRefresh.toString());
            };
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
            exchange.mutate().request(serverHttpRequest).build();
            return chain.filter(exchange);
        }catch (Exception exception){
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

    @Override
    public int getOrder() {
        return 20;
    }

    private Boolean needRefreshToken(String accessToken) throws Exception{
        if(TokenModeEnum.JWT.getValue().equalsIgnoreCase(tokenMode)) {
            return false;
        }else if(TokenModeEnum.REDIS.getValue().equalsIgnoreCase(tokenMode)){
            return redisOnlineUserUtils.getAccessTokenRefreshNeed(accessToken);
        }else{
            throw new JwtUtil.TokenValidationException("令牌丢失了。");
        }
    }
    private String generateUnAuthorizedExcpetJoson(String uri) {
        ResponseException ex = new ResponseException();
        ex.setResponseStatus(ResponseStatus.UNAUTHORIZED);
        ControllerResultDTO controllerResultDTO = ControllerResultDTO.failure(uri, ex,"false");
        return FastjsonUtil.getJson(controllerResultDTO);
    }
}
