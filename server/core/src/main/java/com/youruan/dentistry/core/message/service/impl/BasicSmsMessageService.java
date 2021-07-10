
package com.youruan.dentistry.core.message.service.impl;

import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.utils.ValidationUtils;
import com.youruan.dentistry.core.message.domain.SmsMessage;
import com.youruan.dentistry.core.message.event.SmsMessageCreatedEvent;
import com.youruan.dentistry.core.message.mapper.SmsMessageMapper;
import com.youruan.dentistry.core.message.query.SmsMessageQuery;
import com.youruan.dentistry.core.message.service.SmsMessageService;
import com.youruan.dentistry.core.message.vo.ExtendedSmsMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("smsMessageService")
public class BasicSmsMessageService
    implements SmsMessageService, ApplicationEventPublisherAware
{

    private final SmsMessageMapper smsMessageMapper;
    private ApplicationEventPublisher applicationEventPublisher;

    public BasicSmsMessageService(SmsMessageMapper smsMessageMapper) {
        this.smsMessageMapper = smsMessageMapper;
    }

    @Override
    public SmsMessage get(Long id) {
        return smsMessageMapper.get(id);
    }

    protected void update(SmsMessage smsMessage) {
        int affected = smsMessageMapper.update(smsMessage);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        smsMessage.setVersion((smsMessage.getVersion()+ 1));
    }

    protected SmsMessage add(SmsMessage smsMessage) {
        smsMessage.setCreatedDate(new Date());
        smsMessageMapper.add(smsMessage);
        return smsMessage;
    }

    @Override
    public List<ExtendedSmsMessage> list(SmsMessageQuery qo) {
        return smsMessageMapper.query(qo);
    }

    @Override
    public ExtendedSmsMessage queryOne(SmsMessageQuery qo) {
        qo.setPageSize(1);
        List<ExtendedSmsMessage> data = smsMessageMapper.query(qo);
        return (((data == null)||data.isEmpty())?null:data.get(0));
    }

    @Override
    public Pagination<ExtendedSmsMessage> query(SmsMessageQuery qo) {
        int rows = smsMessageMapper.count(qo);
        List<ExtendedSmsMessage> data = ((rows == 0)?new ArrayList<ExtendedSmsMessage>():smsMessageMapper.query(qo));
        return new Pagination<ExtendedSmsMessage>(rows, data);
    }

    @Override
    public int count(SmsMessageQuery qo) {
        return smsMessageMapper.count(qo);
    }

    @Override
    @Transactional
    public SmsMessage create(String phoneNumber, String templateId, String content) {
        Assert.isTrue(ValidationUtils.isValidPhoneNumber(phoneNumber), "手机号格式有误");

        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setPhoneNumber(phoneNumber);
        smsMessage.setTemplateId(templateId);
        smsMessage.setContent(content);
        smsMessage.setPendingState();
        smsMessage = add(smsMessage);

        applicationEventPublisher.publishEvent(new SmsMessageCreatedEvent(smsMessage));
        return smsMessage;
    }

    @Override
    @Transactional
    public void success(SmsMessage message, String note) {
        Assert.notNull(message, "必须提供消息");
        Assert.isTrue(message.isPendingState(), "状态必须为待发送");

        message.setCloseDate(new Date());
        message.setSuccessState();
        message.setNote(note);
        update(message);
    }

    @Override
    @Transactional
    public void fail(SmsMessage message, String note) {
        Assert.notNull(message, "必须提供消息");
        Assert.isTrue(message.isPendingState(), "状态必须为待发送");

        message.setCloseDate(new Date());
        message.setFailedState();
        message.setNote(note);
        update(message);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
