package mk.anyplex.com.hmvodapi.entry.service;

import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.common.util.EmailUtils;
import mk.anyplex.com.hmvodapi.common.util.RatEmailTemplate;
import mk.anyplex.com.hmvodapi.entry.mapper.EntryMapper;
import mk.anyplex.com.hmvodapi.entry.vo.*;
import mk.anyplex.com.hmvodapi.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  created by andy 2021-03-11
 *  service for entry(TWC/HIR)
 * */
@Service
@Slf4j
public class EntryService implements IEntryService{
    /**
     *  mapper for entry
     * */
    @Resource
    private EntryMapper entryMapper;

    @Resource
    private EmailUtils emailUtils;

    @Override
    @Transactional
    public void saveTwcEntry(AddTwcEntryVo twc) {
        // check twc
        if (!checkTwc(twc)){
            throw  new BusinessException("Missing request parameters");
        }
        try {
            // save twc information
            entryMapper.saveTwcEntry(twc);
            // save twc files
            RatEntryFile entryFile = new RatEntryFile();
            entryFile.setCreate_time(new Date());
            entryFile.setCreator(twc.getCreator());
            entryFile.setType("twc");
            // twc key
            entryFile.setEntry_id(twc.getId());
            String files = twc.getAttachments();
            if (!StringUtils.isEmpty(files)){
                String[] filesArray = files.split(",");
                for (String fileName : filesArray){
                entryFile.setFile_name(fileName);
                // save entry file infomation
                    entryMapper.saveEntryFile(entryFile);
                }
                saveEntryLog("Uploaded files: "+files,twc.getCreator(),twc.getId(),"twc");
            }
            // save  entry log
            saveEntryLog("Created the entry.",twc.getCreator(),twc.getId(),"twc");

            // Send email notification after create twc entry
            if (twc != null && !StringUtils.isEmpty(twc.getCreator()) && !StringUtils.isEmpty(twc.getCreator_email())){
              RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(twc.getId().toString(),"mobility","",twc.getProject_id(),"1",twc.getCreator());
                emailUtils.sendEmail(ratEmailTemplate.getSubject(), twc.getCreator_email(),ratEmailTemplate.getContent());
            }

        }catch (Exception e){
            log.error(e.getMessage());
            throw  new BusinessException("save Twc entry failure");
        }
      return ;
    }

    @Override
    @Transactional
    public void editTwcEntry(EditTwcEntryVo twc) {
        // check twc
        if (!checkEditTwc(twc)){
            throw  new BusinessException("Missing request parameters");
        }
        entryMapper.EditTwcEntry(twc);
        // save twc files
        RatEntryFile entryFile = new RatEntryFile();
        entryFile.setCreate_time(new Date());
        entryFile.setCreator(twc.getCreator());
        entryFile.setType("twc");
        // twc key
        entryFile.setEntry_id(twc.getId());
        String files = twc.getAttachments();
        if (!StringUtils.isEmpty(files)){
            String[] filesArray = files.split(",");
            for (String fileName : filesArray){
                entryFile.setFile_name(fileName);
                // save entry file infomation
                entryMapper.saveEntryFile(entryFile);
            }
            //  save files log
            saveEntryLog("Uploaded files: " + files ,twc.getCreator(),twc.getId(),"twc");
        }
        // save  entry log
        saveEntryLog("Edit the entry.",twc.getCreator(),twc.getId(),"twc");

        // 需要發送email ， 接收人包括當前用戶(send_email) 和創建用戶(creator_email)
        RatTwc twc2 = entryMapper.findTwcById(twc.getId());
        String creator_email = twc2.getCreator_email();
        String send_email = twc.getSend_email();
        String creator = twc.getCreator();
        if (!StringUtils.isEmpty(creator_email) ){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(twc.getId().toString(),"mobility","",twc2.getProject_id(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),creator_email,ratEmailTemplate.getContent());
        }
        if (!StringUtils.isEmpty(send_email) && !send_email.equals(creator_email)){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(twc.getId().toString(),twc.getOc_mobility_type(),"",twc2.getProject_id(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),send_email,ratEmailTemplate.getContent());
        }

    }

    private boolean checkEditTwc(EditTwcEntryVo twc) {
        boolean flag =true;
        if (twc==null || twc.getId() == null || twc.getId()==0){
            flag = false;
        }
        return  flag;
    }

    @Override
    @Transactional
    public void saveHirEntry(AddHirEntryVo hir) {
        // check twc
        if (!checkHir(hir)){
            throw  new BusinessException("Missing request parameters");
        }
        try {
            entryMapper.saveHirEntry(hir);
            // save twc files
            RatEntryFile entryFile = new RatEntryFile();
            entryFile.setCreate_time(new Date());
            entryFile.setCreator(hir.getCreator());
            entryFile.setType("hir");
            // twc key
            entryFile.setEntry_id(hir.getId());
            String files = hir.getAttachments();
            if (!StringUtils.isEmpty(files)){
                String[] filesArray = files.split(",");
                for (String fileName : filesArray){
                    entryFile.setFile_name(fileName);
                    // save entry file infomation
                    entryMapper.saveEntryFile(entryFile);
                }
                //  save files log
                saveEntryLog("Uploaded files: "+ files ,hir.getCreator(),hir.getId(),"hir");
            }
            // save entry log
            saveEntryLog("Created the entry.",hir.getCreator(),hir.getId(),"hir");
            // Send email notification after create hir entry

            if (hir != null && !StringUtils.isEmpty(hir.getCreator()) && !StringUtils.isEmpty(hir.getCreator_email())){
                RatEmailTemplate ratEmailTemplate =new RatEmailTemplate(hir.getId().toString(),"mobility","2",hir.getProject_no(),"1",hir.getCreator());
                emailUtils.sendEmail(ratEmailTemplate.getSubject(), hir.getCreator_email(),ratEmailTemplate.getContent());
            }


        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("save Hir entry failure");
        }

    }

    @Override
    @Transactional
    public void saveHirTempEntry(AddHirEntryVo hir) {
        // check twc
        if (!checkHir(hir)){
            throw  new BusinessException("Missing request parameters");
        }
        try {
            entryMapper.saveHirTempEntry(hir);
            // save twc files
            RatEntryFile entryFile = new RatEntryFile();
            entryFile.setCreate_time(new Date());
            entryFile.setCreator(hir.getCreator());
            entryFile.setType("hir_temp");
            // hir temp key
            entryFile.setEntry_id(hir.getId());
            String files = hir.getAttachments();
            if (!StringUtils.isEmpty(files)){
                String[] filesArray = files.split(",");
                for (String fileName : filesArray){
                    entryFile.setFile_name(fileName);
                    // save entry file infomation
                    entryMapper.saveEntryFile(entryFile);
                }
                //  save files log
                saveEntryLog("Uploaded files: "+ files ,hir.getCreator(),hir.getId(),"hir_temp");
            }
            // save entry log
            saveEntryLog("Created the entry.",hir.getCreator(),hir.getId(),"hir_temp");
            // Send email notification after create hir entry
            if (hir != null && !StringUtils.isEmpty(hir.getCreator()) && !StringUtils.isEmpty(hir.getCreator_email())){
                RatEmailTemplate ratEmailTemplate =new RatEmailTemplate(hir.getId().toString(),"mobility","3",hir.getProject_no(),"1",hir.getCreator());
                emailUtils.sendEmail(ratEmailTemplate.getSubject(), hir.getCreator_email(),ratEmailTemplate.getContent());
            }

        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("save Hir entry failure");
        }

    }

    @Override
    @Transactional
    public void editHirEntry(EditHirEntryVo hir) {
        // check twc
        if (!checkEditHir(hir)){
            throw  new BusinessException("Missing request parameters");
        }
        entryMapper.EditHirEntry(hir);
        // save twc files
        RatEntryFile entryFile = new RatEntryFile();
        entryFile.setCreate_time(new Date());
        entryFile.setCreator(hir.getCreator());
        entryFile.setType("hir");
        // twc key
        entryFile.setEntry_id(hir.getId());
        String files = hir.getAttachments();
        if (!StringUtils.isEmpty(files)){
            String[] filesArray = files.split(",");
            for (String fileName : filesArray){
                entryFile.setFile_name(fileName);
                // save entry file infomation
                entryMapper.saveEntryFile(entryFile);
            }
            //  save files log
            saveEntryLog("Uploaded files: " + files ,hir.getCreator(),hir.getId(),"hir");
        }
        // save  entry log
        saveEntryLog("Edit the entry.",hir.getCreator(),hir.getId(),"hir");

        // send email
        String creator = hir.getCreator();
        // 需要發送email ， 接收人包括當前用戶(send_email) 和創建用戶(creator_email)
        RatHir hir2 = entryMapper.findHirEntryById(hir.getId());
        String creator_email = hir2.getCreator_email();
        String send_email = hir.getSend_email();

        // creator_email , send_email 不應該爲空.
        if (!StringUtils.isEmpty(creator_email) ){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"mobility","2",hir2.getProject_no(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),creator_email,ratEmailTemplate.getContent());
        }
        if (!StringUtils.isEmpty(send_email) && !send_email.equals(creator_email)){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),hir.getOc_mobility_type(),"2",hir2.getProject_no(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),send_email,ratEmailTemplate.getContent());
        }

    }


    @Override
    @Transactional
    public void editHirTempEntry(EditHirEntryVo hir) {
        // check twc
        if (!checkEditHir(hir)){
            throw  new BusinessException("Missing request parameters");
        }
        // hir temp
        entryMapper.EditHirTempEntry(hir);
        // save twc files
        RatEntryFile entryFile = new RatEntryFile();
        entryFile.setCreate_time(new Date());
        entryFile.setCreator(hir.getCreator());
        entryFile.setType("hir_temp");
        // twc key
        entryFile.setEntry_id(hir.getId());
        String files = hir.getAttachments();
        if (!StringUtils.isEmpty(files)){
            String[] filesArray = files.split(",");
            for (String fileName : filesArray){
                entryFile.setFile_name(fileName);
                // save entry file infomation
                entryMapper.saveEntryFile(entryFile);
            }
            //  save files log
            saveEntryLog("Uploaded files: " + files ,hir.getCreator(),hir.getId(),"hir_temp");
        }
        // save  entry log
        saveEntryLog("Edit the entry.",hir.getCreator(),hir.getId(),"hir_temp");

        // 需要發送email ， 接收人包括當前用戶(send_email) 和創建用戶(creator_email)

        RatHirTemp hir2 = entryMapper.findHirTempEntryById(hir.getId());
        String creator_email = hir2.getCreator_email();
        String send_email = hir.getSend_email();
        String creator = hir.getCreator();
        // creator_email , send_email 不應該爲空.
        if (!StringUtils.isEmpty(creator_email) ){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"mobility","3",hir2.getProject_no(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),creator_email,ratEmailTemplate.getContent());
        }
        if (!StringUtils.isEmpty(send_email) && !send_email.equals(creator_email)){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),hir.getOc_mobility_type(),"3",hir2.getProject_no(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),send_email,ratEmailTemplate.getContent());
        }
    }

    private boolean checkEditHir(EditHirEntryVo hir) {
        boolean flag = true;
        if (hir==null || hir.getId()==null || hir.getId()==0){
            flag = false;
        }
        return flag;
    }

    /**
     *   save entry log infomation
     * @param event
     * @param creator
     * @param entry_id
     * @param type
     * */
    @Transactional
    void saveEntryLog(String event,String creator , int entry_id, String type){
        RatEntryLog log =  new RatEntryLog();
       log.setEvent(event);
       log.setCreator(creator);
       log.setEntry_id(entry_id);
       log.setType(type);
       entryMapper.saveEntryLog(log);
    }

    @Override
    public RatTwc findTwcEntry(Integer id) {
        if (id == null || id == 0){
            throw  new BusinessException("Missing request parameters");
        }
        RatTwc twc = null;
        try {
            twc = entryMapper.findTwcById(id);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("find Twc entry information failure! ");
        }
        return twc;
    }

    @Override
    public RatHir findHirEntryById(Integer id) {
        if (id == null || id == 0){
            throw  new BusinessException("Missing request parameters");
        }
        RatHir hir = null;
        try {
            hir = entryMapper.findHirEntryById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("find Hir entry information failure! ");
        }
        return hir;
    }

    @Override
    public RatHirTemp findHirTempEntryById(Integer id) {
        if (id == null || id == 0){
            throw  new BusinessException("Missing request parameters");
        }
        RatHirTemp hir = null;
        try {
            hir = entryMapper.findHirTempEntryById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("find Hir entry information failure! ");
        }
        return hir;
    }

    @Override
    @Transactional
    public void delEntryByIdAndType(Integer id, String type) {
        if (id!= null && id != 0 && !StringUtils.isEmpty(type)){
            if ("twc".equalsIgnoreCase(type)){
                 // del twc entry
                entryMapper.delTwcEntryById(id);
            }else if("hir".equalsIgnoreCase(type)){
                // del hir entry
                entryMapper.delHirEntryById(id);
            }else if ("hir_temp".equals(type)){
                // del hir temp(copy hir) entry
                entryMapper.delHirTempEntryById(id);
            }
        }
    }

    @Override
    public List<RatTwc> searchTwc(SearchTwcVo searchTwcVo) {
        if (searchTwcVo == null){
            searchTwcVo =  new SearchTwcVo();
        }
        if (searchTwcVo.getPage() == null || searchTwcVo.getPage() == 0){
            searchTwcVo.setPage(1);
        }
        if (searchTwcVo.getPage_size() == null || searchTwcVo.getPage_size() == 0){
            searchTwcVo.setPage_size(10);
        }
        searchTwcVo.setStartIndex((searchTwcVo.getPage()-1)*searchTwcVo.getPage_size());
        List <RatTwc> list = entryMapper.searchTwcEntry(searchTwcVo);
        return list;
    }

    /**
     * check Hir entry
     * @param hir  hir entry information
     * @return true: no error  false: error
     * */
    private boolean checkHir(AddHirEntryVo hir) {
        boolean flag = true;
        if (StringUtils.isEmpty(hir.getType())){
            flag = false;
        }
        return flag;
    }

    /**
     *  check twc entry information
     * @param twc
     * @return  true: no error  false: error
     * */
    private boolean checkTwc(AddTwcEntryVo twc) {
        boolean flag = true;
        // creator_email 暫時允許爲空, 爲空，不發 email
        if (twc == null || StringUtils.isEmpty(twc.getType()) || StringUtils.isEmpty(twc.getProject_name()) ){
            flag = false;
        }
        return flag;
    }
}
