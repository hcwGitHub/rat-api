package mk.anyplex.com.hmvodapi.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 异常状态码信息
 * @author hcw
 * @date 2019-8-8
 * */
public final class ErrorCode {
    public static Map<String,String> codeMap;

    static{
        codeMap = new LinkedHashMap(256);

        //系统模块
        codeMap.put("1","SUEECSS");
        codeMap.put("0","請求失敗，請稍後再試！");
        codeMap.put("10", "服務器異常，請稍後再試~");
        codeMap.put("1000","缺少參數，請重試");
        codeMap.put("1001","無效的參數值，請重試");
        codeMap.put("1002","系統出錯啦，請稍後再試");
        codeMap.put("1003","簽名驗證不通過");
        codeMap.put("1004","視頻不存在");
        codeMap.put("1005","用戶已經存在");
        codeMap.put("1006","郵箱已經註冊");
        codeMap.put("1007","郵箱驗證碼發送失敗，請檢查郵箱是否有效");
        codeMap.put("1009","驗證碼錯誤");
        codeMap.put("1010","驗證碼過期");
        codeMap.put("1011","非法的郵箱地址");
        codeMap.put("1012","無效的token,請您重新登陸");
        codeMap.put("1013","用戶不存在，請重新登錄");
        codeMap.put("1014","密碼錯誤");
        codeMap.put("1015","二次輸入密碼不一致");
        codeMap.put("1016","郵箱或密碼錯誤");
        codeMap.put("1017","用戶名不存在");
        codeMap.put("1018","會員處於有效期，暫時不需要購買會員");
        codeMap.put("1019","登陸設備超過三臺,建議找回密碼!!");
        codeMap.put("1020","redis數據讀取異常!!");
        codeMap.put("1021","您沒有訪問權限!!");

    }

    /**
     * private ，禁止创建对象
     * */
    private ErrorCode() {

    }

    public static Map<String, String> getCodeMap() {
        return codeMap;
    }

    public static void setCodeMap(Map<String, String> codeMap) {
        ErrorCode.codeMap = codeMap;
    }
}
