package mk.anyplex.com.hmvodapi.common.filter;

import mk.anyplex.com.hmvodapi.common.tools.BodyReaderHttpServletRequestWrapper;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器（Filter）,Post请求下对request进行包装
 * Filter 在interceptor之前执行
 * 定义filer 可以通过WebFilter 注解定义，参数urlPatterns过滤url
 * 这里统一管理filter,不使用这方式,详情查询FilterConfig配置类
 * @author huangchongwen
 * @date 2019-08-22
 *
 */
//@Component
//@WebFilter(filterName = "httpServletRequestWrapperFilter", urlPatterns = { "/*" })
public class HttpServletRequestWrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            //获取Method
            String methodType = httpRequest.getMethod();

            /**
             * 请求Url路径,必要时过滤Post请求下不包装的情况
             * */
            String servletPath = httpRequest.getRequestURI().toString();
            //对Post请求方式进行封装, 如果是文件上传, 就不需要了
            if ("POST".equals(methodType) && !servletPath.contains("upload")) {

                requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);

            }
        }

        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {

    }
}
