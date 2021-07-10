
package com.youruan.dentistry.core.message.service;

import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.message.domain.SmsVerification;
import com.youruan.dentistry.core.message.query.SmsVerificationQuery;
import com.youruan.dentistry.core.message.vo.ExtendedSmsVerification;

import java.util.List;

public interface SmsVerificationService {


    public SmsVerification get(Long id);

    public ExtendedSmsVerification queryOne(SmsVerificationQuery qo);

    public List<ExtendedSmsVerification> list(SmsVerificationQuery qo);

    public Pagination<ExtendedSmsVerification> query(SmsVerificationQuery qo);

    public int count(SmsVerificationQuery qo);

    boolean checkLogin(String phoneNumber, String smsCode);

    SmsVerification sendLogin(String phoneNumber, String requestIp);
}
