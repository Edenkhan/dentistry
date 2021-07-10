package com.youruan.dentistry.console.message.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.message.query.SmsVerificationQuery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class SmsVerificationListForm extends ListForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String phoneNumber;
    private String requestIp;
    private Integer[] type;
    private Integer[] state;

    public SmsVerificationQuery buildQuery() {
        SmsVerificationQuery qo = new SmsVerificationQuery();
        qo.setPage(getPage());
        qo.setPhoneNumber(phoneNumber);
        qo.setRequestIp(requestIp);
        qo.setIncludeTypes(type);
        qo.setIncludeStates(state);
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);

        if ("createdDate".equals(getSortField())) {
            qo.setOrderById(getSortKeyword());
        }
        return qo;
    }

}
