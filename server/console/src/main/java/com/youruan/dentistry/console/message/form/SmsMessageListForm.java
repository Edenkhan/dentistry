package com.youruan.dentistry.console.message.form;

import com.youruan.dentistry.console.base.form.ListForm;
import com.youruan.dentistry.core.message.query.SmsMessageQuery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class SmsMessageListForm extends ListForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreatedDate;
    private String phoneNumber;
    private Integer[] state;


    public SmsMessageQuery buildQuery() {
        SmsMessageQuery qo = new SmsMessageQuery();
        qo.setPage(getPage());
        qo.setPhoneNumber(phoneNumber);
        qo.setIncludeStates(state);
        qo.setStartCreatedDate(startCreatedDate);
        qo.setEndCreatedDate(endCreatedDate);

        if ("createdDate".equals(getSortField())) {
            qo.setOrderById(getSortKeyword());
        }
        return qo;
    }

}
