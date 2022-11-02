package cn.edu.bit.GSDB.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wjk
 * @since 2022-10-20 11:33:50
 */
@Data
public class PageRspData<T> {

    private List<T> records;

    private Long total;

    private Long pages;

    public static <T> PageRspData<T> of(List<T> records, Long total, Long pages) {
        PageRspData<T> resp = new PageRspData<>();
        resp.setRecords(records);
        resp.setTotal(total);
        resp.setPages(pages);
        return resp;
    }
}
