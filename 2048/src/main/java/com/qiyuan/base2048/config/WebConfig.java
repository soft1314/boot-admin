package com.qiyuan.base2048.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-20 17:01
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor);
    }
}
