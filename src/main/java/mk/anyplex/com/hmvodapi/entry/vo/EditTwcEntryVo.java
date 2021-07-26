package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 *    created  by andy 2021-03-12
 *    edit twc entry vo
 * */
@Data
public class EditTwcEntryVo implements Serializable {
    /**
     * id(primary key),自增长
     * */
    private Integer id;
    /**
     *  (user type)用户类型
     * */
    private String type;
    /**
     *   approve(pending, approved, rejected)
     * */
    private String approve;
    /**
     * (Project Name)项目名称
     * */
    private String project_name;

    /**
     *   name of twc
     * */
    private String twc_name;
    /**
     *  (Project Pm) 项目PM
     * */
    private String project_pm;
    /**
     * (Project SO) 项目SO
     * */
    private String project_so;
    /**
     *  (Academic Qualification (Year Granted)) 学历(授予年)
     * */
    private String academic;

    private String academic_date;
    /**
     * (discipline)学科
     * */
    private String discipline;
    /**
     * (Profession Qualification)专业资格
     * */
    private String profession_qualification;
    /**
     *(Experience After Graduation) 毕业后工作经验
     * */
    private String experience;
    /**
     *（e-mail）邮件
     * */
    private String email;
    /**
     *（Phone No.）电话
     * */
    private String phone_no;
    /**
     * (Remark) 备注信息
     * */
    private String remark;
    /**
     *（Attachments file/file  Name）附件/附件名称
     * */
    private String attachments;
    /**
     * (create user date) 用户创建时间
     * */
    private Date create_time;
    /**
     * (update user date),用户修改信息时间
     * */
    private Date update_time;
    /**
     * default 1(enabled) 0：(disabled)
     * 默认1:(有效)
     * 0（无效）
     * */
    private Integer status;

    /**
     *  mark :
     Not part of the table budget
     * */
    private String creator;

    private String send_email;

    private String  oc_mobility_type;

    /**
     * 當前項目 -> 21/07/2021 修復郵件鏈接
     */
    private String identifier;

    public void setAcademic_date(String academic_date) {
        if (!StringUtils.isEmpty(academic_date))
            this.academic_date = Integer.valueOf(academic_date.substring(0,4)) + 1 +"";
    }

}
