package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.*;
import mk.anyplex.com.hmvodapi.common.bean.PageRequestParamsVO;

import java.io.Serializable;

/**
 *  search twc vo
 *  created by andy 2021-03-15
 * */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchTwcVo extends PageRequestParamsVO implements Serializable {
    /**
     * (Project Name)项目名称
     * */
    private String project_name;

    /**
     *  project _id
     * */
    private String project_id;

    /**
     *  division
     * */
    private String division;

    /**
     *  approve status
     *  2021-06-10 add
     * */
    private String approve;

}
