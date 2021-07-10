package com.youruan.dentistry.core.base.aliyunsms;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("aliyun.sms")
@ConditionalOnProperty(prefix = "aliyun.sms", name = "enable")
@Component
public class AliyunSmsProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignName() {
        return signName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

}
