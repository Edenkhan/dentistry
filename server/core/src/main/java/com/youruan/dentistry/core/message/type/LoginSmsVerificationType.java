package com.youruan.dentistry.core.message.type;

import com.youruan.dentistry.core.message.SmsVerificationProperties;
import com.youruan.dentistry.core.message.domain.SmsVerification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoginSmsVerificationType extends AbstractSmsVerificationType {

    @Value("${dentistry.core.message.sms.verification.types.login.template}")
    private String template;

    @Value("${dentistry.core.message.sms.verification.types.login.templateId:#{null}}")
    private String templateId;

    public LoginSmsVerificationType(SmsVerificationProperties smsVerificationProperties) {
        super(smsVerificationProperties);
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public Integer getType() {
        return SmsVerification.TYPE_LOGIN;
    }

    @Override
    public String getTemplateId() {
        return templateId;
    }

}
