package mk.anyplex.com.hmvodapi.file.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  created by andy 2021-03-10
 *  addFile api pojo
 * */
@Data
public class AddFileVo implements Serializable {
    /**
     *  (file type)文件类型
     * */
    private String type;

    /**
     *mobility 是否展示 1： 展示 0：不展示
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
}
