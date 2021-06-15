package mk.anyplex.com.hmvodapi.common.config;

import mk.anyplex.com.hmvodapi.common.util.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 給PropertiesUtil注入env屬性
 *  --> 相當於spring配置的<bean></bean>
 * @author created by hcw 2020-03-09 20:01
 * */

@Configuration
public class PropertiesConfig {

    @Resource
    private Environment env;

    @PostConstruct
    public void setProperties() {
        PropertiesUtil.setEnvironment(env);
    }

}
