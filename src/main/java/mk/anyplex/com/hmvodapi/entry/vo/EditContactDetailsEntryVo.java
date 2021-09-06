package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Qingnan
 * 02/9/2021
 * edit ContactDetails entry vo
 */
@Data
public class EditContactDetailsEntryVo implements Serializable {
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
     *（Attachments file/file  Name）附件/附件名称
     * */
    private String attachments;
    /**
     * Capacity (Management Staff,TWC,Engineer)
     */
    private String capacity;

    /**
     * (name) 用户名
     */
    private String name;

    /**
     * (phone_no) 用户电话号码
     */
    private String phone_no;

    /**
     * (email) 用户邮箱
     */
    private String email;
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

    /**
     * 當前項目
     */
    private String identifier;
}
