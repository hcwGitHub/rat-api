package mk.anyplex.com.hmvodapi.file.service;

import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.file.mapper.FileMapper;
import mk.anyplex.com.hmvodapi.file.vo.AddFileVo;
import mk.anyplex.com.hmvodapi.pojo.Attachments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FileService implements  IFileService{
    /**
     *  fileMapper
     * */
    @Resource
    private FileMapper fileMapper;

    @Override
    @Transactional
    public Integer addFile(AddFileVo fileVo) {
        int rows = 0;
        if (!isNull(fileVo)){
            if (fileVo.getCreate_time()==null){
                //create_time default now()
                fileVo.setCreate_time(new Date());
            }
            if (fileVo.getDisplay()==null){
                // display default 1
                fileVo.setDisplay(1);
            }
            // created file information
            try {
                fileMapper.createdFileInformation(fileVo);
                rows =1 ;
            }catch (Exception e){
                throw e;
            }

        }else{
            log.error("created file information ->missing request parameters!!!");
           throw  new BusinessException("missing request parameters!!!");
        }
        return rows;
    }

    @Override
    public Attachments findFileById(Integer id) {
         if (id==null || id==0){
             return null;
         }
        Attachments attachments = fileMapper.findFileById(id);
         return attachments;
    }

    @Override
    public List<Attachments> findFileByType(String type,Integer display,Integer page, Integer page_size) {
//        if (display == null){
//            // default 1
//            display = 1;
//        }
        if (page == null || page == 0){
            // default 1
            page =1;
        }
        if (page_size == null || page_size == 0){
            // default 10
            page_size = 10;
        }
        List<Attachments> res = fileMapper.findFilesByType(type, display,(page-1)*page_size,page_size);
        return res;
    }

    @Override
    @Transactional
    public int del( Map<String,Object> map) {
        int rows = 0;
        if (map==null || !map.containsKey("id")){
            return rows;
        }
        try {
            fileMapper.delFileById(Integer.parseInt((String)map.get("id")));
            rows=1;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return rows;
    }

    /**
     *  check fileVo
     * @param fileVo  pojo
     * @return
     * */
    private boolean isNull(AddFileVo fileVo) {
        boolean flag = false;
        //  || StringUtils.isEmpty(fileVo.getFile_name()) || StringUtils.isEmpty(fileVo.getCreator())
        if (fileVo==null || StringUtils.isEmpty(fileVo.getType())){
            flag = true;
        }
        return flag;
    }

}
