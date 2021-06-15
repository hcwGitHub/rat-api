package mk.anyplex.com.hmvodapi.file.mapper;

import mk.anyplex.com.hmvodapi.file.vo.AddFileVo;
import mk.anyplex.com.hmvodapi.pojo.Attachments;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *  created by andy 2021-03-10
 *  file mapper
 * */
@Mapper
public interface FileMapper {

   /**
    *  sql : created file information
    * @param fileVo  file information
    * */
    @Insert("insert into `attachments`(type,display,file_name,creator,create_time,status) values(#{type},#{display},#{file_name},#{creator},#{create_time},1 )")
    void createdFileInformation(AddFileVo fileVo);

    /**
     *   find file by fileId
     * @param id  fileId
     * @return  attachment
     * */
    @Select("Select id,type,file_name,creator,create_time,status from `attachments` where id =#{id}")
    Attachments findFileById(@Param("id") Integer id);

    /**
     *  delete file by fileId
     * @param id fileId
     * */
    @Update("update `attachments`  set status=0 where id =#{id}")
    void delFileById(@Param("id") int id);

    /**
     *  find files by file type
     * @param type file type
     * @param display  1 ： show
     * @param startIndex   start index
     * @param page_size  page size
     * @return Attachments
     * */
    @Select("<script>" +
            "Select id,type,file_name, display, creator,DATE_FORMAT(create_time,'%Y-%m-%d') create_time_str,create_time,status from `attachments` where type =#{type} " +
            "<if test='display != null'>and display=#{display}</if>" +
            " and status =1 order by id desc limit #{startIndex},#{page_size}" +
            "</script>")
    List<Attachments> findFilesByType(@Param("type") String type,@Param("display") Integer display, @Param("startIndex") int startIndex, @Param("page_size") int page_size);

    /**
     *  count countFileByType
     * @param type  file type
     * @param display  display
     * */
    @Select("<script>" +
         "Select count(id) from `attachments` where type =#{type} " +
         "<if test='display != null'>and display=#{display}</if>" +
         " and status =1 order by id desc" +
         "</script>")
     int countFileByType(@Param("type") String type,@Param("display") Integer display);

    /**
     *   update attachments display by id
     * @param id  attachment id
     * @param display  display
     * */
    @Update("update rat.`attachments` set display = #{display} where id = #{id}")
    void updateDisplayStatus(Integer id, Integer display);

    /**
     *  del file by id
     * */
    @Update("update rat.`attachments` set status=0 where id = #{id}")
    void updateStatus(Integer id);

    /**
     *  update all file display status by file type
     * @param display
     * */
    @Update("update rat.`attachments` set display = #{display} where type = #{type}")
    void updateAllFileDisplay(@Param("display") Integer display,@Param("type") String type);

}
