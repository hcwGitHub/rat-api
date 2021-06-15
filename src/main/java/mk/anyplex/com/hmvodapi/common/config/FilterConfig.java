package mk.anyplex.com.hmvodapi.common.config;

import mk.anyplex.com.hmvodapi.common.filter.AccessControlAllowOriginFilter;
import mk.anyplex.com.hmvodapi.common.filter.HttpServletRequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * filter  定义过滤器,执行顺序 etc
 * @author huangchongwen weiai
 * @date 2019-10-21
 * */
@Configuration
public class FilterConfig {

    /**
     * accessControlAllowOriginFilter 跨域filter
     * order filter执行优先级
     * name  filter name
     * */
    @Bean
    public FilterRegistrationBean buildAFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setFilter(new AccessControlAllowOriginFilter());
        filterRegistrationBean.setName("accessControlAllowOriginFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * httpServletRequestWrapperFilter
     * bean -> add bean(entity) to Spring IOC and manger
     * */
    @Bean
    public FilterRegistrationBean buildBFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.setFilter(new HttpServletRequestWrapperFilter());
        filterRegistrationBean.setName("httpServletRequestWrapperFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
