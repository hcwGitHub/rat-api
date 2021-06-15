package mk.anyplex.com.hmvodapi.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决跨域filter,使用filterConfig配置filters
 * 这里定义类，extends Filter 即可
 * @author Ge
 * 2019/10/10
 */

public class AccessControlAllowOriginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,signature, Authorization, access-control-allow-origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Expose-Headers","signature");

        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }

}
