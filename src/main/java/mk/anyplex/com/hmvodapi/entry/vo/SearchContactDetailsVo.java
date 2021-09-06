package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.Data;
import mk.anyplex.com.hmvodapi.common.bean.PageRequestParamsVO;

import java.io.Serializable;

/**
 * @author Qingnan
 * 02/9/2021
 * search hir entry information
 */
@Data
public class SearchContactDetailsVo extends PageRequestParamsVO implements Serializable {
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
