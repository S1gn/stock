package com.s1gn.stock.vo.resp;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageResult
 * @Description 分页结果vo
 * @Author S1gn
 * @Date 16:08
 * @Version 1.0
 */
@Data
public class PageResult<T> implements Serializable {
    /**
     * 总记录数
     */
    private Long totalRows;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 当前第几页
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 当前页记录数
     */
    private Integer size;
    /**
     * 结果集
     */
    private List<T> rows;
    /**
     * @Auther s1gn
     * @Description 分页结果构造函数
     * @Date 2024/3/26 16:09
     * @Param * @param pageInfo
     * @Return * @return {@link null }
     **/
    public PageResult(PageInfo<T> pageInfo)
    {
        totalRows = pageInfo.getTotal();
        totalPages = pageInfo.getPages();
        pageNum = pageInfo.getPageNum();
        pageSize = pageInfo.getPageSize();
        size = pageInfo.getSize();
        rows = pageInfo.getList();
    }
}
