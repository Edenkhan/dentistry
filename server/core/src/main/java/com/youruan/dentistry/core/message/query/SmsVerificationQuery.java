
package com.youruan.dentistry.core.message.query;

import java.util.Date;
import com.youruan.dentistry.core.base.query.QueryCondition;

public class SmsVerificationQuery
    extends QueryCondition
{

    protected com.youruan.dentistry.core.base.query.JoinType joinSmsMessage;
    protected SmsMessageQuery smsMessageQuery;
    protected Long[] ids;
    protected Date startCreatedDate;
    protected Date endCreatedDate;
    protected String phoneNumber;
    protected String requestIp;
    protected Date startExpirationDate;
    protected Date endExpirationDate;
    protected Integer[] includeStates;
    protected Integer[] excludeStates;
    protected Integer[] includeTypes;
    protected Integer[] excludeTypes;

    public boolean hasIds() {
        return (this.ids.length!= 0);
    }

    public com.youruan.dentistry.core.base.query.JoinType getJoinSmsMessage() {
        return this.joinSmsMessage;
    }

    public SmsVerificationQuery setJoinSmsMessage(com.youruan.dentistry.core.base.query.JoinType joinSmsMessage) {
        this.joinSmsMessage = joinSmsMessage;
        return this;
    }

    public SmsMessageQuery getSmsMessageQuery() {
        return this.smsMessageQuery;
    }

    public SmsVerificationQuery setSmsMessageQuery(SmsMessageQuery smsMessageQuery) {
        this.smsMessageQuery = smsMessageQuery;
        return this;
    }

    public SmsVerificationQuery setJoinSmsMessage() {
        this.setJoinSmsMessage(com.youruan.dentistry.core.base.query.JoinType.LEFT);
        return this;
    }

    public SmsVerificationQuery setExpirationDate(Date startExpirationDate, Date endExpirationDate) {
        setStartExpirationDate(startExpirationDate);
        setEndExpirationDate(endExpirationDate);
        return this;
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

    public String getRequestIp() {
        return this.requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Date getStartExpirationDate() {
        return this.startExpirationDate;
    }

    public void setStartExpirationDate(Date startExpirationDate) {
        this.startExpirationDate = startExpirationDate;
    }

    public Date getEndExpirationDate() {
        return this.endExpirationDate;
    }

    public void setEndExpirationDate(Date endExpirationDate) {
        this.endExpirationDate = endExpirationDate;
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

    public Integer[] getIncludeTypes() {
        return this.includeTypes;
    }

    public void setIncludeTypes(Integer[] includeTypes) {
        this.includeTypes = includeTypes;
    }

    public Integer[] getExcludeTypes() {
        return this.excludeTypes;
    }

    public void setExcludeTypes(Integer[] excludeTypes) {
        this.excludeTypes = excludeTypes;
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
