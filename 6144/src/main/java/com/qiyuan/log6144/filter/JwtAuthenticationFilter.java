package com.qiyuan.log6144.filter;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.util.FastjsonUtil;
import com.qiyuan.bautil.util.JwtUtil;
import com.qiyuan.bautil.util.Sm4EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String FREE_PATH_PERFIX = "/api/log/free/**";
    private final static String AUTH_PATH_PERFIX = "/api/log/auth/**";

    private final static PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        logger.error(request.getServletPath());
//        logger.error(request.getMethod());
        try {
            if (PATH_MATCHER.match(AUTH_PATH_PERFIX, request.getServletPath())) {
                String jsonEncString = request.getHeader(JwtUtil.AUTHORIZE_USER);
                String jsonString = Sm4EncryptUtil.decrypt(jsonEncString, JwtUtil.JWT_SM4_PADDING,JwtUtil.JWT_SM4_KEY);
                BaseUser baseUser = FastjsonUtil.getObject(jsonString, BaseUser.class);
                request.setAttribute(JwtUtil.AUTHORIZE_USER, baseUser);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
