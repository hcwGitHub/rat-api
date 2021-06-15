package mk.anyplex.com.hmvodapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 *  created by andy 2021-03-09
 *  文件(附件)类型表: file_type
 * */
@Data
public class FileType implements Serializable {
    /**
     * id(primary key),自增长
     * */
    private Integer id;
    /**
     *  (file type)文件类型
     * */
    private String type;
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
}
