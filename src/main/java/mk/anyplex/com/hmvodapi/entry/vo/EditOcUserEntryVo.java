package mk.anyplex.com.hmvodapi.entry.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Qingnan
 * 30/6/2021
 * edit user entry vo
 */
@Data
public class EditOcUserEntryVo implements Serializable {
    // id(primary key),自增长
    private Integer id;
    // (currentPwd)当前密码
    String currentPwd;
    // (pwd)用户密码
    private String pwd;

    /**
     * 26/07/2021 新需求: oc用戶可以查看和修改自己的個人資料
     * firstName: 名
     * lastName: 姓
     * name: 姓名
     */
    private String name;
    private String firstName;
    private String lastName;

    /**
     * default 1(enabled) 0：(disabled)
     * 默认1:(有效)
     * 0:(无效)
     */
    private Integer status;
}
