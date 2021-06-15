package mk.anyplex.com.hmvodapi.common.tools;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mk.anyplex.com.hmvodapi.common.constants.Sys;

import java.util.HashMap;

/**
 * 封装json数据,resultMap jsonMap
 * @author Created By andy1995 26/12/2019 16:30
 * */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JsonMap  {

    /**
     * result
     * */
    private String result = Sys.SUCCESS;

    /**
     * 响应信息
     * */
    private  String msg = "請求成功";

    /**
     * 响应码
     * */
    private  Integer errorCode = 0;

}
