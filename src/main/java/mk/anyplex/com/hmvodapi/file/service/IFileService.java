package mk.anyplex.com.hmvodapi.file.service;

import mk.anyplex.com.hmvodapi.file.vo.AddFileVo;
import mk.anyplex.com.hmvodapi.pojo.Attachments;

import java.util.List;
import java.util.Map;

public interface IFileService {
    /**
     *created file information
     * @param fileVo  file infomation
     * @return int  Affect the number of rows
     * */
    Integer addFile(AddFileVo fileVo);

    /**
     *  find file by id
     * @param id  file id
     * @return attachment
     * */
    Attachments findFileById(Integer id);

    /**
     *  del file by fileId
     * @param map  fileId
     * @return  int
     * */
    int del(Map<String, Object> map);

    /**
     *  find files by file type, page
     * @param type file type
     * @param display 1: show display
     * @param page page
     * @param page_size  page size
     * @return attachments
     * */
    List<Attachments> findFileByType(String type,Integer display,Integer page,Integer page_size);
}
