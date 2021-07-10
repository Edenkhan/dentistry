package com.youruan.dentistry.core.message.event;

import com.youruan.dentistry.core.base.event.LiveApplicationEvent;
import com.youruan.dentistry.core.message.domain.SmsMessage;

public class SmsMessageCreatedEvent extends LiveApplicationEvent<SmsMessage> {

    public SmsMessageCreatedEvent(SmsMessage smsMessage) {
        super(smsMessage);
    }

}
