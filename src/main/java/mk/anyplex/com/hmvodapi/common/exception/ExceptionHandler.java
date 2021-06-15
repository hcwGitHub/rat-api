package mk.anyplex.com.hmvodapi.common.exception;

import mk.anyplex.com.hmvodapi.common.constants.ErrorCode;
import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.tools.JsonMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理
 * @author hcw
 * @date 2019-8-7
 * */
@ControllerAdvice
public class ExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonMap handler(Exception e) {
        JsonMap res = new JsonMap();
        if (e instanceof  BusinessException){
           res.setErrorCode(1);
           res.setMsg(e.getMessage());
           res.setResult(Sys.FALL);
           log.error(e.getMessage());
        }else  if (e instanceof ConfCenterException) {
            String mes =  ErrorCode.codeMap.get(e.getMessage());
            res.setMsg(mes); //set mes
            int code = Integer.parseInt(e.getMessage());
            res.setErrorCode(code); //set code
            res.setResult(Sys.FALL);
            log.error(mes);
        } else {
            log.error("exception:{}", e.getMessage(), e);
//            res.setMsg("服务器内部错误");
            res.setMsg("服务器内部错误");
            res.setErrorCode(500);
            res.setResult(Sys.FALL);
           log.error("exception:{}", e.getMessage(), e);
        }

        return res;
    }

}
