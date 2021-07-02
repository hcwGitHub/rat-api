package mk.anyplex.com.hmvodapi.entry.mapper;

import mk.anyplex.com.hmvodapi.common.bean.OcAccount;
import mk.anyplex.com.hmvodapi.entry.vo.*;
import mk.anyplex.com.hmvodapi.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * created by andy 2021-03-11
 * mapper for entry(TWC/HIR)
 */
@Mapper
public interface EntryMapper {

    /**
     * save twc entry information
     *
     * @param twc
     */
    @Insert("insert into rat.`rat_twc`(type,project_name,twc_name,division,project_id,project_pm,project_so,academic,academic_date,discipline,profession_qualification,experience,email,creator_email,phone_no,remark,attachments,create_time,status) " +
            "values(#{type},#{project_name},#{twc_name},#{division},#{project_id},#{project_pm},#{project_so},#{academic},#{academic_date},#{discipline},#{profession_qualification},#{experience}," +
            "#{email},#{creator_email},#{phone_no},#{remark},#{attachments},NOW(),1)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveTwcEntry(AddTwcEntryVo twc);

    /**
     * edit twc information
     *
     * @param twc
     */
    @Update("update rat.`rat_twc` " +
            " set type =#{type},approve=#{approve},project_name=#{project_name},twc_name=#{twc_name},project_pm=#{project_pm}," +
            " project_so = #{project_so},academic=#{academic},academic_date=#{academic_date},discipline=#{discipline}," +
            "profession_qualification = #{profession_qualification},experience=#{experience},email=#{email}," +
            "phone_no = #{phone_no},  remark=#{remark}, attachments=#{attachments},update_time=now() where id =#{id}")
    void EditTwcEntry(EditTwcEntryVo twc);

    /**
     * save twc entry information
     *
     * @param hir
     */
    @Insert("insert into rat.`rat_hir`(type,divsion,project_no,project_name,remark,attachments,creator_email,create_time,status) " +
            "values(#{type},#{divsion},#{project_no},#{project_name},#{remark},#{attachments},#{creator_email},now(),1 )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveHirEntry(AddHirEntryVo hir);

    /**
     * save twc entry temp information
     *
     * @param hir
     */
    @Insert("insert into rat.`rat_hir_temp`(type,divsion,project_no,project_name,remark,attachments,creator_email,create_time,status) " +
            "values(#{type},#{divsion},#{project_no},#{project_name},#{remark},#{attachments},#{creator_email},now(),1 )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveHirTempEntry(AddHirEntryVo hir);

    @Update("update rat.`rat_hir` " +
            "  set type=#{type},approve=#{approve},divsion = #{divsion},project_no=#{project_no},project_name=#{project_name}," +
            " remark = #{remark},attachments=#{attachments},update_time=now() where id =#{id}")
    void EditHirEntry(EditHirEntryVo hir);

    @Update("update rat.`rat_hir_temp` " +
            "  set type=#{type},approve=#{approve},divsion = #{divsion},project_no=#{project_no},project_name=#{project_name}," +
            " remark = #{remark},attachments=#{attachments},update_time=now() where id =#{id}")
    void EditHirTempEntry(EditHirEntryVo hir);

    /**
     * save entry file information
     *
     * @param entryFile
     */
    @Insert("insert into rat.`rat_entry_file`(type,file_name,creator,entry_id,create_time,status) " +
            "values(#{type},#{file_name},#{creator},#{entry_id},#{create_time},1)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveEntryFile(RatEntryFile entryFile);

    /**
     * find entry file by entry_id ,  order by create_time desc
     *
     * @param id entry_id
     * @return List<RatEntryFile>
     */
    @Select("select id,type,file_name,creator,entry_id,DATE_FORMAT(create_time,'%Y-%m-%d')  create_time_str,create_time,status from rat.`rat_entry_file` where entry_id = #{id} and status = 1 and type = #{type}  order by id desc")
    List<RatEntryFile> findEntryFileByEntryId(@Param("id") int id, @Param("type") String type);

    /**
     * sql : find twc entry information by id
     *
     * @param id twc_id
     * @return RatTwc
     */
    @Select("select id,type,approve,division,project_id, project_name,twc_name,project_pm,project_so,academic,creator_email," +
            "academic_date,discipline,profession_qualification,experience,email,phone_no," +
            "reason_of_rejected, remark,attachments,create_time,status" +
            " from rat.`rat_twc` where id = #{id}")
    RatTwc findTwcById(@Param("id") int id);

    /**
     * save entry_log
     *
     * @param log
     */
    @Insert("insert into rat.`rat_entry_log`(event,type,creator,entry_id,create_time,status) " +
            "values(#{event},#{type},#{creator},#{entry_id},now(),1)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveEntryLog(RatEntryLog log);

    /**
     * find entry log by entry_id
     *
     * @param id entry_id
     * @return List<RatEntryLog></RatEntryLog>
     */
    @Select("Select id,type,event,creator,entry_id,DATE_FORMAT(create_time,'%Y-%m-%d')  create_time_str, " +
            "create_time,status from rat.`rat_entry_log` where  entry_id = #{id} and status = 1 and type=#{type} order by id desc")
    List<RatEntryLog> findEntryLogByEntryId(@Param("id") int id, @Param("type") String type);

    /**
     * find hir entry by id
     *
     * @param id
     * @return RatHir
     */
    @Select("select id,type,approve,divsion,project_no,project_name,creator_email," +
            "reason_of_rejected ,remark,attachments,create_time,DATE_FORMAT(create_time,'%Y-%m-%d')  create_time_str," +
            " update_time, DATE_FORMAT(update_time,'%Y-%m-%d') as update_time_str,status" +
            " from rat.`rat_hir` where id = #{id} ")
    RatHir findHirEntryById(@Param("id") int id);

    /**
     * find hir temp entry by id
     *
     * @param id
     * @return RatHir
     */
    @Select("select id,type,approve,divsion,project_no,project_name,creator_email," +
            "reason_of_rejected,remark,attachments,create_time,DATE_FORMAT(create_time,'%Y-%m-%d')  create_time_str," +
            " update_time, DATE_FORMAT(update_time,'%Y-%m-%d') as update_time_str,status" +
            " from rat.`rat_hir_temp` where id = #{id} ")
    RatHirTemp findHirTempEntryById(@Param("id") int id);

    /**
     * del twc entry by id
     *
     * @param id
     */
    @Update("update rat.`rat_twc` set status = 0 and update_time = now() where id =#{id}")
    void delTwcEntryById(@Param("id") int id);

    /**
     * del hir entry by id
     *
     * @param id
     */
    @Update("update rat.`rat_hir` set status = 0 and update_time = now() where id =#{id}")
    void delHirEntryById(@Param("id") int id);


    /**
     * del hir temp entry by id
     *
     * @param id
     */
    @Update("update rat.`rat_hir_temp` set status = 0 and update_time = now() where id =#{id}")
    void delHirTempEntryById(@Param("id") int id);

    /**
     * del entry file by id
     *
     * @param id
     */
    @Update("update rat.`rat_entry_file` set status = 0  where id =#{id}")
    void delEntryFileById(@Param("id") int id);

    /**
     * sql: search twc by project_id and division
     *
     * @param searchTwcVo
     * @return List<RatTwc> rat_twc list
     */
    @Select("<script>select * , SUBSTRING(academic_date, 0, 4) as academic_date from rat.`rat_twc` " +
            "  where status=1 " +
            "      <if test='project_id != null and project_id!=\"\" '>and project_id = #{project_id}</if>" +
            "      <if test='division != null and division!=\"\" '>and division = #{division}</if>" +
            "      <if test='approve != null and approve!=\"\" '>and approve = #{approve}</if>" +
            "  order by project_name desc limit #{startIndex},#{page_size}" +
            "</script> ")
    List<RatTwc> searchTwcEntry(SearchTwcVo searchTwcVo);

    /**
     * sql: search hir
     *
     * @param searchHirVo searchHirVo
     * @return List<RatTwc> rat_twc list
     */
    @Select("<script>select *  from rat.`rat_hir`  " +
            "  where status=1 " +
            "      <if test='project_no != null and project_no!=\"\" '>and project_no = #{project_no}</if>" +
            "      <if test='project_name != null and project_name!=\"\" '>and project_name = #{project_name}</if>" +
            "      <if test='division != null and division!=\"\" '>and divsion = #{division}</if>" +
            "      <if test='approve != null and approve!=\"\" '>and approve = #{approve}</if>" +
            "  order by project_name desc limit #{startIndex},#{page_size}" +
            "</script> ")
    List<RatHir> searchHirEntry(SearchHirVo searchHirVo);

    /**
     * sql: search hir temp
     *
     * @param searchHirVo searchHirVo
     * @return List<RatTwc> rat_twc list
     */
    @Select("<script>select *  from rat.`rat_hir_temp`  " +
            "  where status=1 " +
            "      <if test='project_no != null and project_no!=\"\" '>and project_no = #{project_no}</if>" +
            "      <if test='project_name != null and project_name!=\"\" '>and project_name = #{project_name}</if>" +
            "      <if test='division != null and division!=\"\" '>and divsion = #{division}</if>" +
            "      <if test='approve != null and approve!=\"\" '>and approve = #{approve}</if>" +
            "  order by project_name desc limit #{startIndex},#{page_size}" +
            "</script> ")
    List<RatHirTemp> searchHirTempEntry(SearchHirVo searchHirVo);

    /**
     * count rat_twc
     *
     * @param searchTwcVo searchTwcVo
     * @return int  count
     */
    @Select("<script>select count(1) from rat.`rat_twc`  " +
            "  where status=1 " +
            "      <if test='project_id != null and project_id!=\"\" '>and project_id = #{project_id}</if>" +
            "      <if test='division != null and division!=\"\" '>and division = #{division}</if>" +
            "      <if test='approve != null and approve!=\"\" '>and approve = #{approve}</if>" +
            "</script> ")
    int countTwcEntry(SearchTwcVo searchTwcVo);

    /**
     * count rat_hir
     *
     * @param searchHirVo searchHirVo
     * @return int  count
     */
    @Select("<script>select count(1)  from rat.`rat_hir`  " +
            "  where status=1 " +
            "      <if test='project_no != null and project_no!=\"\" '>and project_no = #{project_no}</if>" +
            "      <if test='project_name != null and project_name!=\"\" '>and project_name = #{project_name}</if>" +
            "      <if test='division != null and division!=\"\" '>and divsion = #{division}</if>" +
            "      <if test='approve != null and approve!=\"\" '>and approve = #{approve}</if>" +
            "</script> ")
    int countHirEntry(SearchHirVo searchHirVo);

    /**
     * count rat_hir
     *
     * @param searchHirVo searchHirVo
     * @return int  count
     */
    @Select("<script>select count(1)  from rat.`rat_hir_temp`  " +
            "  where status=1 " +
            "      <if test='project_no != null and project_no!=\"\" '>and project_no = #{project_no}</if>" +
            "      <if test='project_name != null and project_name!=\"\" '>and project_name = #{project_name}</if>" +
            "      <if test='division != null and division!=\"\" '>and divsion = #{division}</if>" +
            "      <if test='approve != null and approve!=\"\" '>and approve = #{approve}</if>" +
            "</script> ")
    int countHirTempEntry(SearchHirVo searchHirVo);

    /**
     * update hir approve status and remark
     */
    @Update("<script> " +
            "update rat.`rat_hir` set approve = #{approve}," +
            "<if test='remark != null'> remark = #{remark}, </if>" +
            "<if test='reason_of_rejected != null'> reason_of_rejected = #{reason_of_rejected}, </if>" +
            " update_time= now() where id = #{id} " +
            "</script>")
    void updateHirApprove(Integer id, String approve, String remark, String reason_of_rejected);


    /**
     * update hir temp(copy hir) approve status and remark
     */
    @Update("<script> " +
            "update rat.`rat_hir_temp` set approve = #{approve}," +
            "<if test='remark != null'> remark = #{remark}, </if>" +
            "<if test='reason_of_rejected != null'> reason_of_rejected = #{reason_of_rejected}, </if>" +
            " update_time= now() where id = #{id} " +
            "</script>")
    void updateHirTempApprove(Integer id, String approve, String remark, String reason_of_rejected);

    /**
     * update twc entry approve status and remark
     */
    @Update("<script>" +
            "update rat.`rat_twc` set approve = #{approve}," +
            "<if test='remark != null'> remark = #{remark}, </if>" +
            "<if test='reason_of_rejected != null'> reason_of_rejected = #{reason_of_rejected}, </if>" +
            "update_time= now() where id = #{id} " +
            "</script>")
    void updateTwcApprove(Integer id, String approve, String remark, String reason_of_rejected);


    /**
     * check oc user login
     *
     * @param account
     * @param pwd
     * @return
     */
    @Select("SELECT id,account,pwd,create_time,update_time,status FROM rat.`rat_oc_user` where account=#{account} and pwd=#{pwd}")
    RatOcUser checkOcUserLogin(String account, String pwd);

    /**
     * Get all users
     * @return
     */
    @Select("SELECT id,account,pwd,create_time,update_time,status FROM rat.`rat_oc_user`")
    List<OcAccount> acList();

    /**
     * update oc user pwd
     * @param userEntryVo
     * @return
     */
    @Update("update rat.`rat_oc_user` set pwd=#{pwd}, update_time=now() where id=#{id} and pwd=#{currentPwd} limit 1")
    int updatePwd(EditOcUserEntryVo userEntryVo);

    /**
     * update oc user status
     * @param id
     * @param status
     */
    @Update("update rat.`rat_oc_user` set status=#{status}, update_time=now() where id=#{id}")
    void updateOcUser(Integer id, Integer status);

}
