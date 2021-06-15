package mk.anyplex.com.hmvodapi.common.interceptors;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.constants.SignatureKey;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.common.exception.ConfCenterException;
import mk.anyplex.com.hmvodapi.common.tools.HttpHelper;
import mk.anyplex.com.hmvodapi.common.tools.ParamHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * MD5数据验签拦截器
 * @author hcw
 * @date 2019-8-13
 */
@Component
@Slf4j
public class Md5ValidInterceptor implements HandlerInterceptor {
    /**
     * preHandle 在执行action之前执行
     * @param request
     * @param response
     * @param handler
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        //if method is options , pass and return true;
        //跨域请求options 方式跳过.
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        //if request is not a method ，pass
        //如果请求不是action,如静态资源,pass
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //if it is file or video  upload , pass
//        String servletPath = request.getRequestURI().toString();
//        if (servletPath.contains("/uploadProfileImg")||servletPath.contains("/uploadVideoPoster")
//        ||servletPath.contains("/m3u8/getm3u8")||servletPath.contains("/uploadVideo")
//        ||servletPath.contains("/getFile")||servletPath.contains("/getUserProfile")
//        )
//        {
//            return true;
//        }
        //get sign
        Map<String, String> header = HttpHelper.getHeadersInfo(request);
        String sign = header.get(SignatureKey.SIGNATURE);

        if (StringUtils.isEmpty(sign)) {
            //signature is null or ''
            StringBuilder sb = new StringBuilder("缺少signature签名");
            log.error(sb.toString());
            throw new BusinessException(sb.toString());
        }
        if (request.getMethod().toUpperCase().equals("GET")) {
            Map<String, String> map = new HashMap<>();
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                map.put(name, request.getParameter(name));
            }
            String signNow = ParamHelper.getSign(map);
            if (!signNow.equals(sign)) {
                //signature、MD5 is fail
                log.error("签名不一致");
                throw new ConfCenterException("1003");
            }

        } else {
            String orgNos = HttpHelper.getBodyString(request);
            //Json String - > json Map
            Map<String, String> map = (Map<String, String>) JSON.parse(orgNos);

            String signNow = ParamHelper.getSign(map);
//            System.out.println(signNow);
            if (!signNow.equals(sign)) {
                //signature、MD5 fail
                log.error("签名不一致");
                throw new ConfCenterException("1003");
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
