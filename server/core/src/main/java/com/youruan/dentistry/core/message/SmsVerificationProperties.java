package com.youruan.dentistry.core.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("dentistry.core.message.sms.verification")
@Setter
@Getter
public class SmsVerificationProperties {

    /**
     * 一个验证码最多可被验证次数
     */
    private Integer maxRetryCount;

    /**
     * 每个 IP 每日最多可发送次数
     */
    private Integer dailyMaximumEachIp;

    /**
     * 每次验证码发送间隔秒数
     */
    private Integer intervalInSeconds;

    /**
     * 默认验证码内容模板
     */
    private String defaultTemplate;

    /**
     * 默认验证码有效分钟数
     */
    private Integer defaultValidityInMinutes;

}
