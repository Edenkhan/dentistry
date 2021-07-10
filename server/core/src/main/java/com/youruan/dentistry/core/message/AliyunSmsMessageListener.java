package com.youruan.dentistry.core.message;

import com.youruan.dentistry.core.base.aliyunsms.AliyunSms;
import com.youruan.dentistry.core.message.domain.SmsMessage;
import com.youruan.dentistry.core.message.event.SmsMessageCreatedEvent;
import com.youruan.dentistry.core.message.service.SmsMessageService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

@Component
@ConditionalOnProperty(prefix = "aliyun.sms", name = "enable")
public class AliyunSmsMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(AliyunSmsMessageListener.class);
    private final AliyunSms aliyunSms;
    private final SmsMessageService smsMessageService;

    @Autowired
    public AliyunSmsMessageListener(AliyunSms aliyunSms, SmsMessageService smsMessageService) {
        this.aliyunSms = aliyunSms;
        this.smsMessageService = smsMessageService;
    }

    @Async
    @TransactionalEventListener
    public void onSmsMessageCreated(SmsMessageCreatedEvent event) {
        SmsMessage smsMessage = event.getSource();
        LOG.info("准备发送短信。ID：{}，手机号：{}，模板：{}, 短信内容：{}", smsMessage.getId(), smsMessage.getPhoneNumber(), smsMessage.getTemplateId(), smsMessage.getContent());
        try {
            aliyunSms.sendSms(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss" + (10000 + smsMessage.getId())),
                    smsMessage.getPhoneNumber(),
                    smsMessage.getTemplateId(),
                    smsMessage.getContent());
            smsMessageService.success(smsMessage, null);
            LOG.info("短信发送成功");
        } catch (Exception e) {
            LOG.error("短信消息发送失败", e);
            smsMessageService.fail(smsMessage, e.getMessage());
        }
    }
}
