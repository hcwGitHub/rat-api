package mk.anyplex.com.hmvodapi.file.controller;


import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *   created by andy 2021-03-09
 *   Informative File upload file api action
 * */
@Controller
@Slf4j
public class UploadFileController {
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

    /**
     *   Informative File  Single/multiple file upload
     * @param myfiles  files
     * @param attribute  file attribute
     *                   attribute=1(default) ： informative file
     *                   attribute= 0 : entry file
     * */
    @PostMapping("/uploadFiles")
    @ResponseBody
    public Map<String,Object> ArrayFiles(@RequestParam("myfiles") MultipartFile[] myfiles,@RequestParam("attribute") String attribute){
        Map<String ,Object> result = new LinkedHashMap<>();
        try {
            StringBuilder builder = new StringBuilder();
            String file_dir_path = "";
            if (StringUtils.isEmpty(attribute)){
                attribute = "1"; // default "1";
            }
            if ("1".equals(attribute)){
                file_dir_path = fileDir;
            }else if ("0".equals(attribute)){
                file_dir_path = entryFileDir;
            }
            if (myfiles!=null && myfiles.length>0) {
                for (int i = 0; i < myfiles.length; i++) {
                    String filename = myfiles[i].getOriginalFilename();
                    builder.append(filename + ",");
                    myfiles[i].transferTo(new File(file_dir_path + filename));
                }
                result.put("result", Sys.SUCCESS);
                result.put("msg",Sys.MESSAGE);
                result.put("file_name",builder.substring(0,builder.lastIndexOf(",")));
            }else{
                // No files uploaded
                log.error("File upload failed!!!:\n" +
                        "No files uploaded");
                throw new BusinessException("File upload failed," +
                        "No files uploaded");
            }
        } catch (IllegalStateException | IOException e) {
            log.error("\"File upload failed!!!\""+ e.getMessage());
            throw new BusinessException("File upload failed!!!");
        }
        return result;
    }



}
