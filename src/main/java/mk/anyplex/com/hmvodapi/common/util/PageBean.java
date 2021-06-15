package mk.anyplex.com.hmvodapi.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页
 * @author ge
 * @date 2019/8/16
 * */
@Getter
@Setter
@ToString
public class PageBean {
    private Integer totalRecords;   //总记录数  从数据库查询
    private Integer pageSize;       //每页显示多少条
    private Integer totalPages;     //总页数   计算出来
    private Integer currentPage;    //当前页码
    private Integer startIndex;     //每页开始查询的索引 计算出来

    /**
     * 计算分页结果
     * @param totalRecords 总记录数
     * @param currentPage 当前页码
     * @param pageSize 每页显示多少条
     */
    public PageBean(Integer totalRecords, Integer currentPage, Integer pageSize){
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRecords = totalRecords;
        this.totalPages = (totalRecords%pageSize==0?  totalRecords/pageSize : totalRecords/pageSize+1);
        this.startIndex = (currentPage-1)*pageSize;
    }

    /**
     * 计算获取总页数
     * @param totalRecords 总记录数
     */
    public void getTotalPages(Integer totalRecords){
        this.totalRecords = totalRecords;
        this.totalPages = (totalRecords%pageSize==0?  totalRecords/pageSize : totalRecords/pageSize+1);
    }
}
