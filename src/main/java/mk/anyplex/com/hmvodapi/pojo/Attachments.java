package mk.anyplex.com.hmvodapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 *  created by andy 2021-03-09
 *  文件(附件)表: attachments
 *  序列化  implements Serializable
 * */
@Data
public class Attachments implements Serializable {
    /**
     * id(primary key),自增长
     * */
    private Integer id;
    /**
     *  (file type)文件类型
     * */
    private String type;
    /**
     * mobility 是否展示 1： 展示 0：不展示
     * */
    private Integer display;
    /**
     * （file name）文件名
     * */
    private String file_name;
    /**
     *Creator(文件创建者/归属者)
     * */
    private String creator;

    /**
     * (create file date) 创建时间
     * */
    private Date create_time;

    /**
     * default 1(enabled) 0：(disabled)
     * 默认1:(有效)
     * 0（无效）
     * */
    private Integer status;

    private String create_time_str;

    private boolean flag ;

    public void setDisplay(Integer display) {
        this.display = display;
        if (display == 1){
            flag = true;
        }else {
            flag = false;
        }
    }
}
