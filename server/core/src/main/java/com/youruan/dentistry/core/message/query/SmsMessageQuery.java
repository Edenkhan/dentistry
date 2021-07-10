
package com.youruan.dentistry.core.message.query;

import java.util.Date;
import com.youruan.dentistry.core.base.query.QueryCondition;

public class SmsMessageQuery
    extends QueryCondition
{

    protected Long[] ids;
    protected Date startCreatedDate;
    protected Date endCreatedDate;
    protected String phoneNumber;
    protected Integer[] includeStates;
    protected Integer[] excludeStates;

    public boolean hasIds() {
        return (this.ids.length!= 0);
    }

    public Long[] getIds() {
        return this.ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Date getStartCreatedDate() {
        return this.startCreatedDate;
    }

    public void setStartCreatedDate(Date startCreatedDate) {
        this.startCreatedDate = startCreatedDate;
    }

    public Date getEndCreatedDate() {
        return this.endCreatedDate;
    }

    public void setEndCreatedDate(Date endCreatedDate) {
        this.endCreatedDate = endCreatedDate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer[] getIncludeStates() {
        return this.includeStates;
    }

    public void setIncludeStates(Integer[] includeStates) {
        this.includeStates = includeStates;
    }

    public Integer[] getExcludeStates() {
        return this.excludeStates;
    }

    public void setExcludeStates(Integer[] excludeStates) {
        this.excludeStates = excludeStates;
    }

    public void setOrderById(int keyword) {
        setOrderBy("id", keyword);
    }

    public int getOrderById() {
        return getOrderByKeyword("id");
    }

    public void setOrderByCreatedDate(int keyword) {
        setOrderBy("createdDate", keyword);
    }

    public int getOrderByCreatedDate() {
        return getOrderByKeyword("createdDate");
    }

}
