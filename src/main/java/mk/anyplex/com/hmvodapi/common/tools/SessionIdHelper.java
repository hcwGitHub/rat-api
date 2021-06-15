package mk.anyplex.com.hmvodapi.common.tools;

import java.util.Random;
import java.util.UUID;

/**
 * 生成唯一的16位uuid
 * @author huangchongwen weiai
 * 2019-9-5 21:10
 * */
public final class SessionIdHelper {
    /**
     * 生成一个16位SessionId
     * @return String sessionId
     * */
    public  static String createSession() {
        int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return first + String.format("%015d", hashCodeV);
    }
}
