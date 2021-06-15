package mk.anyplex.com.hmvodapi.common.exception;

/**
 * BusinessException exception 拓展
 * @author hcw
 * @date 26/12/2019 16:52
 * */
public class BusinessException extends ConfCenterException{

    public BusinessException() {
    }

    /**
     * @param mes 异常信息
     * */
    public BusinessException(String mes) {
       super(mes);
    }
}
