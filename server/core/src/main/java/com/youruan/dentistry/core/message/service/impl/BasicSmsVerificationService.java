
package com.youruan.dentistry.core.message.service.impl;

import com.youruan.dentistry.core.base.exception.OptimismLockingException;
import com.youruan.dentistry.core.base.query.Pagination;
import com.youruan.dentistry.core.base.query.QueryCondition;
import com.youruan.dentistry.core.base.utils.ValidationUtils;
import com.youruan.dentistry.core.message.SmsVerificationProperties;
import com.youruan.dentistry.core.message.domain.SmsMessage;
import com.youruan.dentistry.core.message.domain.SmsVerification;
import com.youruan.dentistry.core.message.exception.SmsVerificationSendBusyException;
import com.youruan.dentistry.core.message.mapper.SmsVerificationMapper;
import com.youruan.dentistry.core.message.query.SmsVerificationQuery;
import com.youruan.dentistry.core.message.service.SmsMessageService;
import com.youruan.dentistry.core.message.service.SmsVerificationService;
import com.youruan.dentistry.core.message.type.SmsVerificationType;
import com.youruan.dentistry.core.message.type.SmsVerificationTypeManager;
import com.youruan.dentistry.core.message.vo.ExtendedSmsVerification;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service("smsVerificationService")
public class BasicSmsVerificationService
        implements SmsVerificationService {
    private static final Logger LOG = LoggerFactory.getLogger(BasicSmsVerificationService.class);

    private final SmsVerificationMapper smsVerificationMapper;
    private final SmsVerificationTypeManager smsVerificationTypeManager;
    private final SmsMessageService smsMessageService;
    private final SmsVerificationProperties smsVerificationProperties;

    public BasicSmsVerificationService(SmsVerificationMapper smsVerificationMapper, SmsVerificationTypeManager smsVerificationTypeManager, SmsMessageService smsMessageService, SmsVerificationProperties smsVerificationProperties) {
        this.smsVerificationMapper = smsVerificationMapper;
        this.smsVerificationTypeManager = smsVerificationTypeManager;
        this.smsMessageService = smsMessageService;
        this.smsVerificationProperties = smsVerificationProperties;
    }

    @Override
    public SmsVerification get(Long id) {
        return smsVerificationMapper.get(id);
    }

    protected void update(SmsVerification smsVerification) {
        int affected = smsVerificationMapper.update(smsVerification);
        if (affected == 0) {
            throw new OptimismLockingException("version!!");
        }
        smsVerification.setVersion((smsVerification.getVersion() + 1));
    }

    protected SmsVerification add(SmsVerification smsVerification) {
        smsVerification.setCreatedDate(new Date());
        smsVerificationMapper.add(smsVerification);
        return smsVerification;
    }

    @Override
    public List<ExtendedSmsVerification> list(SmsVerificationQuery qo) {
        return smsVerificationMapper.query(qo);
    }

    @Override
    public ExtendedSmsVerification queryOne(SmsVerificationQuery qo) {
        qo.setPageSize(1);
        List<ExtendedSmsVerification> data = smsVerificationMapper.query(qo);
        return (((data == null) || data.isEmpty()) ? null : data.get(0));
    }

    @Override
    public Pagination<ExtendedSmsVerification> query(SmsVerificationQuery qo) {
        int rows = smsVerificationMapper.count(qo);
        List<ExtendedSmsVerification> data = ((rows == 0) ? new ArrayList<ExtendedSmsVerification>() : smsVerificationMapper.query(qo));
        return new Pagination<ExtendedSmsVerification>(rows, data);
    }

    @Override
    public int count(SmsVerificationQuery qo) {
        return smsVerificationMapper.count(qo);
    }

    @Override
    @Transactional
    public boolean checkLogin(String phoneNumber, String smsCode) {
        return check(SmsVerification.TYPE_LOGIN, phoneNumber, smsCode);
    }

    @Override
    @Transactional
    public SmsVerification sendLogin(String phoneNumber, String requestIp) {
        return sendCode(SmsVerification.TYPE_LOGIN, phoneNumber, requestIp);
    }

    private SmsVerification sendCode(Integer type, String phoneNumber, String requestIp) {
        Assert.isTrue(ValidationUtils.isValidPhoneNumber(phoneNumber), "手机号格式有误");
        Assert.notNull(requestIp, "请求 IP 不能为空");

        SmsVerificationType smsVerificationType = smsVerificationTypeManager.of(type);
        Assert.notNull(smsVerificationType, "验证码类型有误");

        Date now = new Date();

        // 同一手机号 ? 分钟只可发送 1 条
        {
            Integer intervalInSeconds = smsVerificationProperties.getIntervalInSeconds();
            SmsVerification lastSmsVerification = getLastByTypeAndPhoneNumber(type, phoneNumber);
            if (lastSmsVerification != null) {
                long lastSendTime = lastSmsVerification.getCreatedDate().getTime();
                long nextTime = lastSendTime + intervalInSeconds * 1000;
                if (now.getTime() < nextTime) {
                    throw new SmsVerificationSendBusyException(intervalInSeconds + " 秒内不可重复发送", nextTime);
                }

                if (lastSmsVerification.isPendingState()) {
                    revoke(lastSmsVerification);
                }
            }
        }

        // 同一 IP ? 分钟只可发送 1 条
        {
            Integer intervalInSeconds = smsVerificationProperties.getIntervalInSeconds();
            SmsVerification lastSmsVerification = getLastByRequestIp(requestIp);
            if (lastSmsVerification != null) {
                long lastSendTime = lastSmsVerification.getCreatedDate().getTime();
                long nextTime = lastSendTime + intervalInSeconds * 1000;
                if (now.getTime() < nextTime) {
                    throw new SmsVerificationSendBusyException(intervalInSeconds + " 秒内不可重复发送", nextTime);
                }
            }
        }

        // 同一 IP 一天只能发送 ? 条
        {
            Integer dailyMaximumEachIp = smsVerificationProperties.getDailyMaximumEachIp();
            int todaySent = countTodaySentByRequestIp(requestIp);
            if (todaySent >= dailyMaximumEachIp) {
                throw new SmsVerificationSendBusyException("今日已发送 " + dailyMaximumEachIp + " 条验证短信");
            }
        }

        String code = generateCode();
        String content = smsVerificationType.getContent(code);
        SmsMessage smsMessage = smsMessageService.create(
                phoneNumber,
                smsVerificationType.getTemplateId(),
                content);

        LOG.debug("[验证码发送] {} · {}", phoneNumber, content);

        SmsVerification smsVerification = new SmsVerification();
        smsVerification.setSmsMessageId(smsMessage.getId());
        smsVerification.setPhoneNumber(phoneNumber);
        smsVerification.setCode(code);
        smsVerification.setRequestIp(requestIp);
        smsVerification.setRetryCount(0);
        smsVerification.setExpirationDate(smsVerificationType.getExpirationDate(now));
        smsVerification.setPendingState();
        smsVerification.setType(type);
        return add(smsVerification);
    }

    private String generateCode() {
        Random r = new Random();
        return String.format("%06d", r.nextInt(1000000));
    }

    /**
     * 统计指定 IP 今日已发送验证码次数
     *
     * @param requestIp 指定 IP
     * @return 今日已发送验证码次数
     */
    private int countTodaySentByRequestIp(String requestIp) {
        SmsVerificationQuery qo = new SmsVerificationQuery();
        qo.setRequestIp(requestIp);
        qo.setStartCreatedDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        return count(qo);
    }

    private boolean check(Integer type, String phoneNumber, String smsCode) {
        Date now = new Date();

        SmsVerification smsVerification = getLastByTypeAndPhoneNumber(type, phoneNumber);

        if (smsVerification == null) {
            LOG.debug("[短信验证码验证] · {} 验证码不存在", smsCode);
            return false;
        }

        if (!smsVerification.isPendingState()) {
            LOG.debug("[短信验证码验证] · {} 验证码已无效", smsCode);
            return false;
        }

        if (now.after(smsVerification.getExpirationDate())) {
            LOG.debug("[短信验证码验证] · {} 验证码已过期", smsCode);
            revoke(smsVerification);
            return false;
        }

        if (!smsVerification.getCode().equals(smsCode)) {
            LOG.debug("[短信验证码验证] · {} 验证码不正确", smsCode);
            incrementRetryCount(smsVerification);
            return false;
        }

        success(smsVerification);
        return true;
    }

    private void success(SmsVerification smsVerification) {
        Assert.isTrue(smsVerification.isPendingState(), "状态必须为待验证");
        smsVerification.setSuccessState();
        update(smsVerification);
    }

    private void incrementRetryCount(SmsVerification smsVerification) {
        smsVerification.setRetryCount(smsVerification.getRetryCount() + 1);
        if (smsVerification.getRetryCount().equals(smsVerificationProperties.getMaxRetryCount())) {
            revoke(smsVerification);
            return;
        }
        update(smsVerification);
    }

    private void revoke(SmsVerification smsVerification) {
        Assert.isTrue(smsVerification.isPendingState(), "状态必须为待验证");
        smsVerification.setRevokeState();
        update(smsVerification);
    }

    private SmsVerification getLastByTypeAndPhoneNumber(Integer type, String phoneNumber) {
        SmsVerificationQuery qo = new SmsVerificationQuery();
        qo.setOrderById(QueryCondition.ORDER_BY_KEYWORD_DESC);
        qo.setIncludeTypes(new Integer[]{type});
        qo.setPhoneNumber(phoneNumber);
        return queryOne(qo);
    }

    private SmsVerification getLastByRequestIp(String requestIp) {
        SmsVerificationQuery qo = new SmsVerificationQuery();
        qo.setOrderById(QueryCondition.ORDER_BY_KEYWORD_DESC);
        qo.setRequestIp(requestIp);
        return queryOne(qo);
    }
}
