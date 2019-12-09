package com.cjk.sys.entity;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public class Page {
    /**
     * 每页显示的数量
     */
    private Integer pageSize = 3;

    //总记录数
    private Integer count;

    //总页数
    private Integer pageCount;

    //当前页
    private Integer pageCurrent;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageCount() {
        return (this.count % this.pageSize == 0) ? (this.count / this.pageSize) : (this.count / this.pageSize + 1);
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }
}
