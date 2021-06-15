package mk.anyplex.com.hmvodapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 *  created by andy 2021-03-09
 *  HIR用户表: rat_hir
 * */
@Data
public class RatHir implements Serializable {
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
     *  (Divsion) 分配
     * */
    private String divsion;

    /**
     * (Project No) 项目编号
     * */
    private String project_no;

    /**
     * (Project Name)项目名称
     * */
    private String project_name;

    /**
     * reason_of_rejected
     * */
    private String reason_of_rejected;

    /**
     * (Remark) 备注信息
     * */
    private String remark;
    /**
     *（Attachments file/file  Name）附件/附件名称
     * */
    private String attachments;
    /**
     * email, twc創建者,twc 對象變更發郵件通知
     * 該屬性直接讀取mobility api 獲取, 不需要前端填寫.
     * */
    private String creator_email;
    /**
     * (create user date) 用户创建时间
     * */
    private Date create_time;

    private String create_time_str ;

    /**
     * (update user date),用户修改信息时间
     * */
    private Date update_time;

    /**
     *   update time  string
     * */
    private String update_time_str;
    /**
     * default 1(enabled) 0：(disabled)
     * 默认1:(有效)
     * 0（无效）
     * */
    private Integer status;

}
