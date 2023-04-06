package com.qiyuan.bautil.annotate.response.impl;

import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.annotate.response.pojo.ControllerResultDTO;
import com.qiyuan.bautil.annotate.response.pojo.ResponseException;
import com.qiyuan.bautil.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.WebUtils;

import java.lang.annotation.Annotation;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-08-25 10:32
 **/
@Slf4j
@RestControllerAdvice
public class ResponseBodyAdviceInControolerImpl implements ResponseBodyAdvice<Object> {
    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseBodyInController.class;

    /** 判断类或者方法是否使用了 @ResponseResultBody */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /** 当类或者方法使用了 @ResponseResultBody 就会调用这个方法 */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof ControllerResultDTO) {
            return body;
        }
        String tokenTimeout = "false";
        Object overTimeObj = serverHttpRequest.getHeaders().get(JwtUtil.AUTHORIZE_TOKEN_TIMEOUT);
        if (overTimeObj != null) {
            String overTime = overTimeObj.toString();
            if ("[true]".equalsIgnoreCase(overTime)) {
                tokenTimeout = "true";
            }
        }
        return ControllerResultDTO.success(serverHttpRequest.getURI().getPath(),body,tokenTimeout);
    }

    /**
     * 提供对标准Spring MVC异常的处理
     *
     * @param ex      the target exception
     * @param request the current request
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ControllerResultDTO<?>> exceptionHandler(Exception ex, WebRequest request) {
        log.error("ExceptionHandler: {}", ex.getMessage(),ex);
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof ResponseException) {
            return this.handleResultException((ResponseException) ex, headers, request);
        }
        // TODO: 2019/10/05 galaxy 这里可以自定义其他的异常拦截
        return this.handleException(ex, headers, request);
    }

    /** 对ResultException类返回返回结果的处理 */
    protected ResponseEntity<ControllerResultDTO<?>> handleResultException(ResponseException ex, HttpHeaders headers, WebRequest request) {
        log.error("handleResultException: {}", ex.getMessage(),ex);
        String tokenTimeout = "false";
        String overTime = request.getHeader(JwtUtil.AUTHORIZE_TOKEN_TIMEOUT);
        if("[true]".equalsIgnoreCase(overTime)){
            tokenTimeout = "true";
        }
        ControllerResultDTO<?> body = ControllerResultDTO.failure(((ServletWebRequest) request).getRequest().getRequestURI(),ex,tokenTimeout);
        HttpStatus status = ex.getResponseStatus().getHttpStatus();
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /** 异常类的统一处理 */
    protected ResponseEntity<ControllerResultDTO<?>> handleException(Exception ex, HttpHeaders headers, WebRequest request) {
        log.error("handleException: {}", ex.getMessage(),ex);
        String tokenTimeout = "false";
        String overTime = request.getHeader(JwtUtil.AUTHORIZE_TOKEN_TIMEOUT);
        if("[true]".equalsIgnoreCase(overTime)){
            tokenTimeout = "true";
        }
        ControllerResultDTO<?> body = ControllerResultDTO.failure(((ServletWebRequest) request).getRequest().getRequestURI(),ex,tokenTimeout);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleExceptionInternal(java.lang.Exception, java.lang.Object, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
     * <p>
     * A single place to customize the response body of all exception types.
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     */
    protected ResponseEntity<ControllerResultDTO<?>> handleExceptionInternal(
            Exception ex, ControllerResultDTO<?> body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleExceptionInternal: {}", ex.getMessage(),ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }


}