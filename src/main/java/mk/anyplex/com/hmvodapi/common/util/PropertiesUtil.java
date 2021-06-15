package mk.anyplex.com.hmvodapi.common.util;

import org.springframework.core.env.Environment;
/**
 * Properties配置文件信息
 * -->給簡單pojo對象使用
 * @author created by hcw
 * @date 2020-03-09 17:02
 * */
public class PropertiesUtil {

    private static Environment env = null;

    public static void setEnvironment(Environment env) {
        PropertiesUtil.env = env;
    }

    public static String getProperty(String key) {
        return PropertiesUtil.env.getProperty(key);
    }

}