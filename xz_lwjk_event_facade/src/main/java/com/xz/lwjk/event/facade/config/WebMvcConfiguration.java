/*
package com.xz.lwjk.event.facade.config;

import com.xz.lwjk.event.facade.Intercptor.LoggerIntercptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

*/
/**
 * @auther: huran
 * @Date: 2018/8/10 11:39
 * @Description:拦截器配置
 *//*

//@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    LoggerIntercptor loggerIntercptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerIntercptor).addPathPatterns("/**")
        .excludePathPatterns("/webjars/**","/swagger-**","/v2/**","/swagger-resources/**");
        super.addInterceptors(registry);
    }
}
*/
