package mk.anyplex.com.hmvodapi.common.config;

import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.interceptors.Md5ValidInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.List;

/**
 *  Md5 etc interceptor
 * @author Created by hcw 30/12/2019
 * WebMvcConfigurationSupport 可配置拦截器/参数注解/静态资源等,
 * 并且Springboot只能继承一次
 * slf4j 拿到log日志对象, Lombok方式
 * */
@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * md5 Interceptor
     * */
    @Autowired
    private Md5ValidInterceptor md5ValidInterceptor;

    /**
     * override 添加拦截器
     * @param registry
     * */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // Md5,拦截所有请求 /**
//        log.info("md5ValidInterceptor is  running");
//        registry.addInterceptor(md5ValidInterceptor).addPathPatterns("/**");

    }

    /**
     * addArgumentResolvers 参数解析器
     * @param argumentResolvers
     * */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        log.info("currentUserMethodArgumentResolver is  running");
//        argumentResolvers.add(currentUserMethodArgumentResolver);
    }

}
