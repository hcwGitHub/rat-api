package mk.anyplex.com.hmvodapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 *  created by andy 2021-03-09
 *  TWC/HIR file  - table rat_entry_file
 * */
@Data
public class RatEntryFile implements Serializable {
    /**
     * id(primary key),自增长
     * */
    private Integer id;
    /**
     *  (file type)文件类型
     * */
    private String type;

    /**
     *   entryId
     * */
    private Integer entry_id;

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

    private String create_time_str;

    /**
     * default 1(enabled) 0：(disabled)
     * 默认1:(有效)
     * 0（无效）
     * */
    private Integer status;

}
