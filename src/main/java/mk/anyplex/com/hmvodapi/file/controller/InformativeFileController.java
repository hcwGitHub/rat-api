package mk.anyplex.com.hmvodapi.file.controller;

import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.file.mapper.FileMapper;
import mk.anyplex.com.hmvodapi.file.service.IFileService;
import mk.anyplex.com.hmvodapi.file.vo.AddFileVo;
import mk.anyplex.com.hmvodapi.pojo.Attachments;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *   created by andy 2021-03-09
 *   Informative File api action
 * */
@RestController
@Slf4j
public class InformativeFileController {
    /**
     *  fileMapper
     * */
    @Resource
    private FileMapper fileMapper;

    /**
     *   informative files directory
     * */
    @Value("${informative.file.path}")
    private String fileDir;

    /**
     *   entry files directory
     * */
    @Value("${entry.file.path}")
    private String entryFileDir;

    /***
     *  file Service
     */
    @Autowired
    private IFileService fileService;

    /**
     *  add file
     * @param fileVo
     * */
   @PostMapping("/addFile")
   public Map<String,Object> addFiles(@RequestBody AddFileVo fileVo) {
       // result
       Map<String,Object> result = new LinkedHashMap<>();
       int rows = fileService.addFile(fileVo);
       if (rows > 0){
           result.put("result",Sys.SUCCESS);
           result.put("msg","created file information successfully ! ");
       }else{
           result.put("result",Sys.FALL);
           result.put("msg","Failed to add file information, Please contact the administrator");
       }
       return result;
   }

   /**
    *  download file
    * @param fileName
    *      * @param attribute  file attribute
    *      *                   attribute=1(default) ： informative file
    *      *                   attribute= 0 : entry file
    * */
    @RequestMapping("/download")
    public void fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName,@RequestParam("attribute") String attribute) throws Exception{
        String file_dir_path = "";
        if (StringUtils.isEmpty(attribute)){
            attribute = "1"; // default "1";
        }
        if ("1".equals(attribute)){
            file_dir_path = fileDir;
        }else if ("0".equals(attribute)){
            file_dir_path = entryFileDir;
        }
        File file = new File( file_dir_path+ fileName);
        if(!file.exists()){
            log.error("file does not exist");
           throw new BusinessException("file does not exist, download failed!!");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        // fileName 中文编码问题
        String downloadName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("file does not exist");
            throw new BusinessException("file does not exist, download failed!!");
        }

    }

   @GetMapping("/findFileDetail")
   public Map<String,Object> findFile(@RequestParam("id") Integer file_id){
       Map<String,Object> result = new LinkedHashMap<>();
       Attachments attachments = fileService.findFileById(file_id);
       if (attachments==null){
           throw  new RuntimeException("file does not exist");
       }else{
           result.put("result",Sys.SUCCESS);
           result.put("msg",Sys.MESSAGE);
           result.put("attachments",attachments);
       }
      return result;
   }

   /**
    *   find files by file type , page
    * @param type  file type
    * @param display  1: show display
    * @param page  page
    * @param page_size  page size
    * */
    @GetMapping("/findFilesByType")
    public Map<String,Object> searchFileByType(@RequestParam("type") String type,@RequestParam("display") Integer display,
    @RequestParam("page") Integer page, @RequestParam("page_size") Integer page_size){
        Map<String,Object> result = new LinkedHashMap<>();
       List<Attachments> attachments = fileService.findFileByType(type,display,page,page_size);
       int count  = fileMapper.countFileByType(type,display);
        int pageTotal = count %page_size ==0?count/page_size : ( count/page_size + 1);
       result.put("result",Sys.SUCCESS);
       result.put("msg",Sys.MESSAGE);
       result.put("attachments",attachments);
       result.put("count",count);
       result.put("pageTotal",pageTotal);
       result.put("page",page);
       result.put("page_size",page_size);
       return result;
    }
    /**
     *   update  file display status
     * @param map
     * id attachmentId
     * display 1 : show 0: not show
     * */
    @PostMapping("/updateDisplayStatus")
    public Map<String,Object> updateDisplayStatus(@RequestBody  Map<String, Object> map){
        Map<String,Object> result =  new LinkedHashMap<>();
        if (map == null || !map.containsKey("id") || !map.containsKey("display")){
            throw  new BusinessException("Missing request parameters");
        }
        fileMapper.updateDisplayStatus((Integer) map.get("id"), (Integer) map.get("display"));
        result.put("result",Sys.SUCCESS);
        result.put("msg",Sys.MESSAGE);
        return result;
    }

    /**
     * update all  file display status
     * @param map
     * id attachmentId
     * display 1 : show 0: not show
     * */
    @PostMapping("/updateAllFileDisplayStatus")
    public Map<String,Object> updateAllDisplayStatus(@RequestBody Map<String, Object> map){
        Map<String,Object> result = new LinkedHashMap<>(4);
        if (map == null && ! map.containsKey("display") && !map.containsKey("type")){
            throw  new BusinessException("Missing request parameters");
        }
        fileMapper.updateAllFileDisplay((Integer) map.get("display"),(String) map.get("type"));
        result.put("result",Sys.SUCCESS);
        result.put("msg",Sys.MESSAGE);
        return result;
    }

    /**
     *   del file
     * */
    @PostMapping("/del")
    public Map<String,Object> updateStatus(@RequestBody  Map<String, Object> map){
        Map<String,Object> result =  new LinkedHashMap<>();
        if (map == null || !map.containsKey("id") ){
            throw  new BusinessException("Missing request parameters");
        }
        fileMapper.updateStatus((Integer) map.get("id"));
        result.put("result",Sys.SUCCESS);
        result.put("msg",Sys.MESSAGE);
        return result;
    }


   @PostMapping("/delFile")
   public Map<String,Object> delFiles(@RequestBody  Map<String, Object> map){
       Map<String,Object> res = new LinkedHashMap<>();
       int rows = fileService.del(map);
       if (rows==0){
           throw  new BusinessException("file does not exist");
       }else{
           res.put("result",Sys.SUCCESS);
           res.put("msg",Sys.MESSAGE);
       }
       return res;
   }


}
