package com.youruan.dentistry.core.frontdesk;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat.pay")
@Getter
@Setter
public class WechatPayProperties {
    private String appId;
    private String appSecret;
    private String mchid;
    private String privateKey;
    private String notifyUrl;
}
