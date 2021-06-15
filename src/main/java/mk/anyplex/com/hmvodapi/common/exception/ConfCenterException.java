package mk.anyplex.com.hmvodapi.common.exception;

/**
 * 可知的业务异常，我们直接抛出此类异常
 * @author Created By hcw
 * @date 26/12/2019 16:32
 * */
public class ConfCenterException extends RuntimeException{
    /**
     * uid
     */
    private static final long serialVersionUID = 8037891447646609768L;

    public ConfCenterException() {
    }
    /**
     * 构造函数
     * @param  errMsg 异常消息
     */
    public ConfCenterException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     * @param cause 原始异常
     */
    public ConfCenterException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param errMsg 异常消息
     * @param cause 原始异常
     */
    public ConfCenterException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }

}
