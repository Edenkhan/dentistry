package com.youruan.dentistry.console.platform.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.platform.query.EmployeeQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EmployeeListForm extends ListForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String realName;
    private String phoneNumber;
    private Boolean locked;

    public EmployeeQuery buildQuery() {
        EmployeeQuery qo = new EmployeeQuery();
        qo.setPage(getPage());
        qo.setLikeRealName(realName);
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setLikePhoneNumber(phoneNumber);
        qo.setLocked(locked);
        if ("createdDate".equals(getSortField())) {
            qo.setOrderByCreatedDate(getSortOrder().equalsIgnoreCase("descend")
                    ? QueryCondition.ORDER_BY_KEYWORD_DESC
                    : QueryCondition.ORDER_BY_KEYWORD_ASC);
        }
        return qo;
    }

    public void setStartCreatedDate(Date startCreatedDate) {
        this.startCreatedDate = startCreatedDate;
    }

    public void setEndCreatedDate(Date endCreatedDate) {
        this.endCreatedDate = endCreatedDate;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
