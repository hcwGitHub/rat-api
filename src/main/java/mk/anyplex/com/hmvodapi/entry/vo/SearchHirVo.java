package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.Data;
import mk.anyplex.com.hmvodapi.common.bean.PageRequestParamsVO;

import java.io.Serializable;

/**
 *   search hir entry information
 *   created  by andy 2021-03-15
 * */
@Data
public class SearchHirVo  extends PageRequestParamsVO implements Serializable {
    /**
     * (Project No) 项目编号
     * */
    private String project_no;
    /**
     * (Project Name)项目名称
     * */
    private String project_name;

    private String division;

    private String approve;

}
