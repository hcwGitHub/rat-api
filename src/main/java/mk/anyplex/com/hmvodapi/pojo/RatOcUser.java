package mk.anyplex.com.hmvodapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Qingnan
 * 30/6/2021
 * user用户表: rat_oc_user
 */
@Data
public class RatOcUser implements Serializable {
    // id(primary key),自增长
    private Integer id;
    // (account)用户账号
    private String account;
    // (pwd)用户密码
    private String pwd;
    // (create user date) 用户创建时间
    private Date create_time;
    // (update user date) 用户更新时间
    private Date update_time;
    /**
     * default 1(enabled) 0：(disabled)
     * 默认1:(有效)
     * 0:(无效)
     */
    private Integer status;
}
