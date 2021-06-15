package mk.anyplex.com.hmvodapi.common.tools;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.constants.SignatureKey;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 请求参数Helper、MD5工具
 * @author huangchongwen
 * @date 2019-08-22
 */
@Slf4j
public final class ParamHelper {

    /**
     * private Can`t create an Object
     * */
    private  ParamHelper(){

    }

    /**
     * 获取签名 Map -> String sign
     * @param map
     * @return String sign
     */
    public static String getSign(Map<String, String> map) {
        try {
//            if (map == null)
//                map = new HashMap<>();
//            map.putAll(getCommonData());

            //排序
            List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
            //升序排序
            Collections.sort(list, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
            // ordered true
            JSONObject object = new JSONObject(true);
            for (Map.Entry<String,String> obj : list){
                String key = obj.getKey();
                String value = obj.getValue();
                object.put(key,value);
            }
//            for (int i = 0; i < list.size(); i++) {
//                Map.Entry<String, String> stringObjectEntry = list.get(i);
//                String key = stringObjectEntry.getKey();
//                String value = map.get(key);
//                object.put(key, value);
//            }
            return getSign(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取签名： JSONObject->String sign
     * @param object
     * @return String signature
     */

    public static String getSign(JSONObject object) {

        //deal JSONObject value is null , uses SerializerFeature.WriteMapNullValue
        String jsonStr =  JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);

        log.info("--->jsonStr =" + jsonStr);

        String signature = md5(md5(jsonStr + SignatureKey.ANDROID_KEY)).toUpperCase();

//        log.info("--->signature = " + signature);

        return signature;
    }

    /**
     * MD5加密，32位算法 JsonStr --> String sign
     * @param str Json String
     * @return String signature
     */
    public static String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();

        StringBuilder md5StrBuff = new StringBuilder();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();

    }

}
