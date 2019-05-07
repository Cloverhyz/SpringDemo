package com.kangning.demo.framework.mybatis;

/**
 * @author 加康宁 Date: 2019-04-24 Time: 11:10
 * @version $Id$
 */
public class PageParam {

    private Integer page = 1;

    private Integer pageSize = 20;

    private Integer currentSize;

    private Integer totalCount;

    private Integer totalPage;

    private Boolean useFlag = true;

    private Boolean checkFlag;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Integer currentSize) {
        this.currentSize = currentSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Override
    public String toString() {
        return "PageParam{" +
            "page=" + page +
            ", pageSize=" + pageSize +
            ", currentSize=" + currentSize +
            ", totalCount=" + totalCount +
            ", totalPage=" + totalPage +
            ", useFlag=" + useFlag +
            ", checkFlag=" + checkFlag +
            '}';
    }
}
