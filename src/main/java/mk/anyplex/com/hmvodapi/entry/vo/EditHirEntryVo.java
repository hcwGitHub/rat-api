package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *   created by andy 2021-03-12
 *   edit hir entry vo
 * */
@Data
public class EditHirEntryVo implements Serializable {
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
     file creator
     * */
    private String creator;

    /**
     *  當前登陸用戶Email 地址
     * */
    private String  send_email;

    private String  oc_mobility_type;
}
