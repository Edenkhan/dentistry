package com.youruan.dentistry.core.message.type;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SmsVerificationTypeManager {

    private Map<Integer, SmsVerificationType> smsVerificationTypeMap;

    public SmsVerificationTypeManager(List<SmsVerificationType> smsVerificationTypes) {
        smsVerificationTypeMap = new HashMap<>();
        for (SmsVerificationType smsVerificationType : smsVerificationTypes) {
            smsVerificationTypeMap.put(smsVerificationType.getType(), smsVerificationType);
        }
    }

    public SmsVerificationType of(Integer type) {
        return smsVerificationTypeMap.get(type);
    }

}
