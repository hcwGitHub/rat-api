package mk.anyplex.com.hmvodapi.entry.control;

import lombok.extern.slf4j.Slf4j;
import mk.anyplex.com.hmvodapi.common.bean.OcAccount;
import mk.anyplex.com.hmvodapi.common.constants.Sys;
import mk.anyplex.com.hmvodapi.common.exception.BusinessException;
import mk.anyplex.com.hmvodapi.common.tools.CheckOcAccount;
import mk.anyplex.com.hmvodapi.common.util.EmailUtils;
import mk.anyplex.com.hmvodapi.common.util.RatEmailTemplate;
import mk.anyplex.com.hmvodapi.entry.mapper.EntryMapper;
import mk.anyplex.com.hmvodapi.entry.service.IEntryService;
import mk.anyplex.com.hmvodapi.entry.vo.*;
import mk.anyplex.com.hmvodapi.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *   created  by andy 2021-03-11
 *   TWC entry/ HIR entry manger api
 * */
@RestController
@Slf4j
public class EntryController {
    /**
     *  Service for entry(TWC/HIR)
     * */
    @Autowired
    private IEntryService entryService;

    /**
     *   email tools
     * */
    @Resource
    private EmailUtils emailUtils;

    /**
     *  mapper for entry
     * */
    @Resource
    private EntryMapper entryMapper;

    /**
     *  add TWC entry
     *  url: /addTwcEntry
     * @param twc  Twc entry information
     * */
    @PostMapping("/addTwcEntry")
    public Map<String,Object> addTwcEntry(@RequestBody AddTwcEntryVo twc){
        Map<String,Object> res = new LinkedHashMap<>();
        entryService.saveTwcEntry(twc);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    @PostMapping("/EditTwcEntry")
    public Map<String,Object> editTwcEntry(@RequestBody EditTwcEntryVo twc){
        Map<String,Object> res = new LinkedHashMap<>();
        entryService.editTwcEntry(twc);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *  add Hir entry
     *  url: /addHirEntry
     * @param hir  Hir entry information
     * */
    @PostMapping("/addHirEntry")
    public Map<String,Object> addHirEntry(@RequestBody AddHirEntryVo hir){
        Map<String,Object> res = new LinkedHashMap<>();
        entryService.saveHirEntry(hir);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *  add Hir entry temp
     *  url: /addHirTempEntry
     * @param hir  Hir entry information
     * */
    @PostMapping("/addHirTempEntry")
    public Map<String,Object> addHirTempEntry(@RequestBody AddHirEntryVo hir){
        Map<String,Object> res = new LinkedHashMap<>();
        entryService.saveHirTempEntry(hir);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }


    @PostMapping("/EditHirEntry")
    public Map<String,Object> editHirEntry(@RequestBody EditHirEntryVo hir){
        Map<String,Object> res = new LinkedHashMap<>();
        entryService.editHirEntry(hir);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    @PostMapping("/EditHirTempEntry")
    public Map<String,Object> editHirTempEntry(@RequestBody EditHirEntryVo hir){
        Map<String,Object> res = new LinkedHashMap<>();
        entryService.editHirTempEntry(hir);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *  edit hir entry approve status
     * @param map
     * */
    @PostMapping("/editHirEntryApprove")
    public Map<String,Object> editHirEntry(@RequestBody Map<String,Object> map){
        Map<String,Object> res = new LinkedHashMap<>();
        String remark = null;
        if (map!=null && map.containsKey("remark")){
            remark = (String)map.get("remark");
            remark = "".equals(remark)  ? null: remark;
        }
        // update approve
        String approve = (String) map.get("approve");
        int id = (Integer) map.get("id");
        String creator = (String) map.get("creator");

        // if approve is rejected ,update 'reason_of_rejected'
        // else update remark
        if ("rejected".equalsIgnoreCase(approve)){
            entryMapper.updateHirApprove(id,approve, null,remark);
        }else {
            entryMapper.updateHirApprove(id, approve, remark,null);
        }
        // save logs
        RatEntryLog log = new RatEntryLog();
        log.setType("hir");
        log.setEvent("update entry status to " + approve);
        log.setEntry_id(id);
        log.setCreator(creator);
        entryMapper.saveEntryLog(log);
        // 需要發送email ， 接收人包括當前用戶(send_email) 和創建用戶(creator_email)
        RatHir hir = entryMapper.findHirEntryById(id);
        String creator_email = hir.getCreator_email();
        String send_email = (String) map.get("send_email");
        // creator_email , send_email 不應該爲空.
        if (!StringUtils.isEmpty(creator_email) ){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"mobility","2",hir.getProject_no(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),creator_email,ratEmailTemplate.getContent());
        }
//        if (!StringUtils.isEmpty(send_email) && !send_email.equals(creator_email)){
//            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"oc","2",hir.getProject_no(),"2",creator);
////            emailUtils.sendEmail(ratEmailTemplate.getSubject(),send_email,ratEmailTemplate.getContent());
//            // 群发oc dept email
//            emailUtils.sendEmailOcDept(ratEmailTemplate.getSubject(),ratEmailTemplate.getContent());
//        }

        // 群发 OC  -- 新需求 2021-06-16
        RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"oc","2",hir.getProject_no(),"2",creator);
        emailUtils.sendEmailOcDept(ratEmailTemplate.getSubject(),ratEmailTemplate.getContent());

        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *  edit hir temp (copy hir) entry approve status
     * @param map
     * */
    @PostMapping("/editHirTempEntryApprove")
    public Map<String,Object> editHirTempEntry(@RequestBody Map<String,Object> map){
        Map<String,Object> res = new LinkedHashMap<>();
        String remark = null;
        if (map!=null && map.containsKey("remark")){
            remark = (String)map.get("remark");
            remark = "".equals(remark)  ? null: remark;
        }
        // update approve
        String approve = (String) map.get("approve");
        int id = (Integer) map.get("id");
        String creator  = (String) map.get("creator");
        // if approve is rejected ,update 'reason_of_rejected'
        // else update remark
        if ("rejected".equalsIgnoreCase(approve)){
            entryMapper.updateHirTempApprove(id,approve,null,remark);
        }else{
            entryMapper.updateHirTempApprove(id,approve,remark,null);
        }

        // save logs
        RatEntryLog log = new RatEntryLog();
        log.setType("hir_temp");
        log.setEvent("update entry status to " + (String) map.get("approve"));
        log.setEntry_id((Integer) map.get("id"));
        log.setCreator(creator);
        entryMapper.saveEntryLog(log);
        // 需要發送email ， 接收人包括當前用戶(send_email) 和創建用戶(creator_email)
        RatHirTemp hir = entryMapper.findHirTempEntryById(id);
        String creator_email = hir.getCreator_email();
        String send_email = (String) map.get("send_email");
        // creator_email , send_email 不應該爲空.
        if (!StringUtils.isEmpty(creator_email) ){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"mobility","3",hir.getProject_no(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),creator_email,ratEmailTemplate.getContent());
        }
//        if (!StringUtils.isEmpty(send_email) && !send_email.equals(creator_email)){
//            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"oc","3",hir.getProject_no(),"2",creator);
////            emailUtils.sendEmail(ratEmailTemplate.getSubject(),send_email,ratEmailTemplate.getContent());
//            emailUtils.sendEmailOcDept(ratEmailTemplate.getSubject(),ratEmailTemplate.getContent());
//        }

        // 群发 OC  -- 新需求 2021-06-16
        RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(hir.getId().toString(),"oc","3",hir.getProject_no(),"2",creator);
        emailUtils.sendEmailOcDept(ratEmailTemplate.getSubject(),ratEmailTemplate.getContent());

        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *  edit twc entry approve status
     * @param map
     * */
    @PostMapping("/editTwcEntryApprove")
    public Map<String,Object> editTwcEntry(@RequestBody Map<String,Object> map){
        Map<String,Object> res = new LinkedHashMap<>();
        String remark = null;
        if (map!=null && map.containsKey("remark")){
            remark = (String) map.get("remark");
            remark = "".equals(remark)  ? null: remark;
        }
        String approve = (String) map.get("approve") ;
        int id = (Integer) map.get("id");
        String creator = (String) map.get("creator");
        // if approve is 'rejected', update reason_of_rejected
        // else update remark;
        if ("rejected".equalsIgnoreCase(approve)){
            entryMapper.updateTwcApprove(id,approve,null,remark);
        }else {
            entryMapper.updateTwcApprove(id,approve,remark,null);
        }
//        entryMapper.updateTwcApprove((Integer) map.get("id"),(String) map.get("approve"),remark);
        RatEntryLog log = new RatEntryLog();
        // save log
        log.setType("twc");
        log.setEvent("update entry status to " + approve);
        log.setEntry_id(id);
        log.setCreator(creator);
        entryMapper.saveEntryLog(log);

        // 需要發送email ， 接收人包括當前用戶(send_email) 和創建用戶(creator_email)
        RatTwc twc = entryMapper.findTwcById(id);
        String creator_email = twc.getCreator_email();
        String send_email = (String) map.get("send_email");
        // creator_email , send_email 不應該爲空.
        if (!StringUtils.isEmpty(creator_email) ){
            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(twc.getId().toString(),"mobility","",twc.getProject_id(),"2",creator);
            emailUtils.sendEmail(ratEmailTemplate.getSubject(),creator_email,ratEmailTemplate.getContent());
        }
//        if (!StringUtils.isEmpty(send_email) && !send_email.equals(creator_email)){
//            RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(twc.getId().toString(),"oc","",twc.getProject_id(),"2",creator);
////            emailUtils.sendEmail(ratEmailTemplate.getSubject(),send_email,ratEmailTemplate.getContent());
//            // testing my email and an Oc account
//            emailUtils.sendEmail(ratEmailTemplate.getSubject(),"972606984@qq.com",ratEmailTemplate.getContent());
//            emailUtils.sendEmail(ratEmailTemplate.getSubject(),"lam.ngarmingdavid@chunwo.com",ratEmailTemplate.getContent());
//
//            // 群发
//            emailUtils.sendEmailOcDept(ratEmailTemplate.getSubject(),ratEmailTemplate.getContent());
//        }
        // 群发 OC  -- 新需求 2021-06-16
        RatEmailTemplate ratEmailTemplate =  new RatEmailTemplate(twc.getId().toString(),"oc","",twc.getProject_id(),"2",creator);
        emailUtils.sendEmailOcDept(ratEmailTemplate.getSubject(),ratEmailTemplate.getContent());

//        emailUtils.sendEmail(ratEmailTemplate.getSubject(),"972606984@qq.com",ratEmailTemplate.getContent());
//        emailUtils.sendEmail(ratEmailTemplate.getSubject(),"sean.wan@chunwo.com",ratEmailTemplate.getContent());

        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *   find Twc entry information by Twc_id
     * @param id  twc_id
     * */
    @GetMapping("/findTwcEntryDetail")
    public Map<String,Object> findTwcEntryDetail(@RequestParam("id") Integer id){
        Map<String,Object> res = new LinkedHashMap<>();
        RatTwc twc = entryService.findTwcEntry(id);
        List<RatEntryFile> files = entryMapper.findEntryFileByEntryId(id,"twc");
        List<RatEntryLog> logs = entryMapper.findEntryLogByEntryId(id,"twc");
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        res.put("entry",twc);
        res.put("files",files);
        res.put("logs",logs);
        return res;
    }

    /**
     *   find Hir entry information by hir_id
     * @param id  hir_id
     * */
    @GetMapping("/findHirEntryDetail")
    public Map<String,Object> findHirEntryDetail(@RequestParam("id") Integer id){
        Map<String,Object> res = new LinkedHashMap<>();
        RatHir hir = entryService.findHirEntryById(id);
        List<RatEntryLog> logs = entryMapper.findEntryLogByEntryId(id,"hir");
        List<RatEntryFile> files = entryMapper.findEntryFileByEntryId(id,"hir");
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        res.put("entry",hir);
        res.put("files",files);
        res.put("logs",logs);
        return res;
    }

    /**
     *   find Hir temp entry information by hir_id
     * @param id  hir_id
     * */
    @GetMapping("/findHirTempEntryDetail")
    public Map<String,Object> findHirTempEntryDetail(@RequestParam("id") Integer id){
        Map<String,Object> res = new LinkedHashMap<>();
        RatHirTemp hir = entryService.findHirTempEntryById(id);
        List<RatEntryLog> logs = entryMapper.findEntryLogByEntryId(id,"hir_temp");
        List<RatEntryFile> files = entryMapper.findEntryFileByEntryId(id,"hir_temp");
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        res.put("entry",hir);
        res.put("files",files);
        res.put("logs",logs);
        return res;
    }

    /**
     *   del entry
     * @param map
     *   id --  entry id
     *   type -- hir/twc/hir_temp
     * */
    @PostMapping("/delEntry")
    public Map<String,Object> delEntry(@RequestBody  Map<String, Object> map){
        Map<String,Object> res = new LinkedHashMap<>();
        if (map == null || !map.containsKey("id") || !map.containsKey("type")|| StringUtils.isEmpty((String)map.get("type"))){
            throw  new BusinessException("Missing request parameters");
        }
        entryService.delEntryByIdAndType((int)(map.get("id")), (String)map.get("type"));

        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     * del entry file by file_id
     * @param map
     *  id file_id
     * */
    @PostMapping("/delEntryFile")
    public Map<String,Object> delHirEntry(@RequestBody  Map<String, Object> map){
        Map<String,Object> res = new LinkedHashMap<>();
        if (map == null || !map.containsKey("id")){
            throw  new BusinessException("Missing request parameters");
        }
        entryMapper.delEntryFileById((Integer) (map.get("id")));
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        return res;
    }

    /**
     *  search twc entry
     * @param searchTwcVo  search twc vo
     * @return map data
     * */
    @GetMapping("/searchTwc")
    public Map<String,Object> searchTwc(SearchTwcVo searchTwcVo){
        Map<String,Object> res =  new LinkedHashMap<>();
        if (searchTwcVo == null){
            searchTwcVo =  new SearchTwcVo();
        }
        List<RatTwc> list = entryService.searchTwc(searchTwcVo);
        int count = entryMapper.countTwcEntry(searchTwcVo);
        int pageTotal = count % (searchTwcVo.getPage_size()) ==0?count/searchTwcVo.getPage_size() : ( count/searchTwcVo.getPage_size() + 1);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        res.put("twc_list",list);
        res.put("count",count);
        res.put("pageTotal",pageTotal);
        res.put("page",searchTwcVo.getPage());
        res.put("page_size",searchTwcVo.getPage_size());
        return res;
    }

    /**
     *  search hir entry
     * @param searchHirVo  search hir vo
     * @return map data
     * */
    @GetMapping("/searchHir")
    public Map<String,Object> searchHir(SearchHirVo searchHirVo){
        Map<String,Object> res =  new LinkedHashMap<>();

        if (searchHirVo == null){
            searchHirVo =  new SearchHirVo();
        }
        searchHirVo.setStartIndex((searchHirVo.getPage()-1)*searchHirVo.getPage_size());
        List<RatHir> list = entryMapper.searchHirEntry(searchHirVo);
        int count = entryMapper.countHirEntry(searchHirVo);
        int pageTotal = count % (searchHirVo.getPage_size()) ==0?count/searchHirVo.getPage_size() : ( count/searchHirVo.getPage_size() + 1);
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        res.put("hir_list",list);
        res.put("count",count);
        res.put("pageTotal",pageTotal);
        res.put("page",searchHirVo.getPage());
        res.put("page_size",searchHirVo.getPage_size());
        return res;
    }

    /**
     *  search hir temp entry
     * @param searchHirVo  search hir vo
     * @return map data
     * */
    @GetMapping("/searchHirTemp")
    public Map<String,Object> searchHirTemp(SearchHirVo searchHirVo){
        Map<String,Object> res =  new LinkedHashMap<>();

        if (searchHirVo == null){
            searchHirVo =  new SearchHirVo();
        }
        searchHirVo.setStartIndex( (searchHirVo.getPage()-1) * searchHirVo.getPage_size() );
        List<RatHirTemp> list = entryMapper.searchHirTempEntry(searchHirVo);
        int count = entryMapper.countHirTempEntry(searchHirVo);
        int pageTotal = count % (searchHirVo.getPage_size()) ==0 ? count/searchHirVo.getPage_size() : ( count/searchHirVo.getPage_size() + 1 );
        res.put("result", Sys.SUCCESS);
        res.put("msg",Sys.MESSAGE);
        res.put("hir_list",list);
        res.put("count",count);
        res.put("pageTotal",pageTotal);
        res.put("page",searchHirVo.getPage());
        res.put("page_size",searchHirVo.getPage_size());
        return res;
    }

    /**
     *   oc login
     * @param ac  Rat oc account
     * */
    @GetMapping("/ocLogin")
    public Map<String,Object> delHirEntry(OcAccount ac){
        Map<String,Object> res =  new LinkedHashMap<>(8);
        if(CheckOcAccount.check(ac.getAccount(),ac.getPwd())){
            res.put("result", Sys.SUCCESS);
            res.put("msg",Sys.MESSAGE);
        }else{
            res.put("result", Sys.FALL);
            res.put("msg","Incorrect account or password");
        }

        return res;
    }


}
