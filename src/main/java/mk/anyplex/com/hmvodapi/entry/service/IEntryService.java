package mk.anyplex.com.hmvodapi.entry.service;

import mk.anyplex.com.hmvodapi.entry.vo.*;
import mk.anyplex.com.hmvodapi.pojo.RatHir;
import mk.anyplex.com.hmvodapi.pojo.RatHirTemp;
import mk.anyplex.com.hmvodapi.pojo.RatTwc;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 *  created by andy 2021-03-11
 *  service for entry(TWC/HIR)
 * */
public interface IEntryService {

    /**
     *  add twc entry information
     * @param twc
     * */
    void saveTwcEntry(AddTwcEntryVo twc);

    /**
     *  edit twc entry information
     * @param twc
     * */
    void editTwcEntry(EditTwcEntryVo twc);

    /**
     *  add hir entry information
     * @param hir
     * */
    void saveHirEntry(AddHirEntryVo hir);

    /**
     *  add hir temp entry
     * */
    void saveHirTempEntry(AddHirEntryVo hir);

    /**
     *  edit hir information
     * @param hir
     * */
    void editHirEntry(EditHirEntryVo hir);

    /**
     *  edit hir temp(copy hir) information
     * @param hir
     * */
    void editHirTempEntry(EditHirEntryVo hir);

    /**
     *    find twc entry information by id
     * @param id  twc_id
     * @Return twc entry
     * */
    RatTwc findTwcEntry(Integer id);

    /**
     *    find hir entry information by id
     * @param id  hir_id
     * @Return twc entry
     * */
    RatHir findHirEntryById(Integer id);

    /**
     *    find hir temp entry information by id
     * @param id  hir_id
     * @Return twc entry
     * */
    RatHirTemp findHirTempEntryById(Integer id);

    /**
     *   del entry information by id and type
     * @param id
     * @param type
     * */
    void delEntryByIdAndType(Integer id, String type);

    /**
     *   search twc
     * @param searchTwcVo  twc
     * @return  List<RatTwc>  RatTwc list
     * */
    List<RatTwc> searchTwc(SearchTwcVo searchTwcVo);

}
