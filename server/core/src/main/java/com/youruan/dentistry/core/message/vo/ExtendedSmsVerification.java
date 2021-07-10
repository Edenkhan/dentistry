
package com.youruan.dentistry.core.message.vo;

import com.youruan.dentistry.core.message.domain.SmsVerification;

public class ExtendedSmsVerification
    extends SmsVerification
{

    private final static long serialVersionUID = 1L;
    protected com.youruan.dentistry.core.message.domain.SmsMessage smsMessage;

    public com.youruan.dentistry.core.message.domain.SmsMessage getSmsMessage() {
        return this.smsMessage;
    }

    public void setSmsMessage(com.youruan.dentistry.core.message.domain.SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }

}
