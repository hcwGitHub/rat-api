package mk.anyplex.com.hmvodapi.common.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PageRequestParamsVO extends RequsetParamsVO implements Serializable {

    /**
     * page
     * */
    private Integer page = 1;

    /**
     * page_size
     * */
    private Integer page_size =10 ;

    /**
     *  startIndex
     * */
    private Integer startIndex = (page-1)*page_size;



}
