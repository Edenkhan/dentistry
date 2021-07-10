package com.youruan.dentistry.console.platform.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.platform.query.PermissionQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PermissionListForm extends ListForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String name;

    public PermissionQuery buildQuery() {
        PermissionQuery qo = new PermissionQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setLikeName(name);

        if ("createdDate".equals(getSortField())) {
            qo.setOrderById(getSortOrder().equalsIgnoreCase("descend")
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

    public void setName(String name) {
        this.name = name;
    }
}
