
package com.youruan.dentistry.core.platform.query;

import java.util.Date;
import com.youruan.dentistry.core.base.query.QueryCondition;

public class EmployeeQuery
    extends QueryCondition
{

    protected Long[] ids;
    protected Date startCreatedDate;
    protected Date endCreatedDate;
    protected String username;
    protected String likeUsername;
    protected String likeRealName;
    protected String phoneNumber;
    protected String likePhoneNumber;
    protected Boolean locked;

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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLikeUsername() {
        return this.likeUsername;
    }

    public void setLikeUsername(String likeUsername) {
        this.likeUsername = likeUsername;
    }

    public String getLikeRealName() {
        return this.likeRealName;
    }

    public void setLikeRealName(String likeRealName) {
        this.likeRealName = likeRealName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLikePhoneNumber() {
        return this.likePhoneNumber;
    }

    public void setLikePhoneNumber(String likePhoneNumber) {
        this.likePhoneNumber = likePhoneNumber;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
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
