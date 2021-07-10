package com.youruan.dentistry.console.user.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.user.query.RegisteredUserQuery;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
public class UserListForm extends ListForm<RegisteredUserQuery> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String realName;
    private String phoneNumber;
    private Integer state;

    public RegisteredUserQuery buildQuery() {
        RegisteredUserQuery qo = new RegisteredUserQuery();
        qo.setPage(getPage());
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);
        qo.setLikeRealName(realName);
        qo.setLikePhoneNumber(phoneNumber);
        qo.setState(state);
        if ("createdDate".equals(getSortField())) {
            qo.setOrderByCreatedDate(getSortKeyword());
        }
        return qo;
    }
}
