
package com.youruan.dentistry.core.message.service;

import java.util.List;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.message.domain.SmsMessage;
import com.youruan.dentistry.core.message.query.SmsMessageQuery;
import com.youruan.dentistry.core.message.vo.ExtendedSmsMessage;
import org.springframework.transaction.annotation.Transactional;

public interface SmsMessageService {


    public SmsMessage get(Long id);

    public ExtendedSmsMessage queryOne(SmsMessageQuery qo);

    public List<ExtendedSmsMessage> list(SmsMessageQuery qo);

    public Pagination<ExtendedSmsMessage> query(SmsMessageQuery qo);

    public int count(SmsMessageQuery qo);

    @Transactional
    SmsMessage create(String phoneNumber, String templateId, String content);

    void success(SmsMessage message, String note);

    void fail(SmsMessage message, String note);
}
