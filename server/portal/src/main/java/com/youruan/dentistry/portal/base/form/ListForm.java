package com.youruan.dentistry.portal.base.form;

import com.youruan.dentistry.core.base.query.QueryCondition;

public abstract class ListForm<Q> {

    private Integer page;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

    public abstract Q buildQuery();

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

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortKeyword() {
        return "descend".equalsIgnoreCase(getSortOrder())
                ? QueryCondition.ORDER_BY_KEYWORD_DESC
                : QueryCondition.ORDER_BY_KEYWORD_ASC;
    }
}
