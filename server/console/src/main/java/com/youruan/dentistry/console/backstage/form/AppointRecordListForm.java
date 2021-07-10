package com.youruan.dentistry.console.backstage.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.frontdesk.query.AppointmentQuery;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
public class AppointRecordListForm extends ListForm<AppointmentQuery> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String realName;
    private String phoneNumber;
    private String shopName;
    private Integer state;

    public AppointmentQuery buildQuery() {
        AppointmentQuery qo = new AppointmentQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setLikeRealName(realName);
        qo.setLikePhoneNumber(phoneNumber);
        qo.setLikeShopName(shopName);
        qo.setState(state);
        if ("createdDate".equals(getSortField())) {
            qo.setOrderByCreatedDate(getSortOrder().equalsIgnoreCase("descend")
                    ? QueryCondition.ORDER_BY_KEYWORD_DESC
                    : QueryCondition.ORDER_BY_KEYWORD_ASC);
        }
        return qo;
    }
}
