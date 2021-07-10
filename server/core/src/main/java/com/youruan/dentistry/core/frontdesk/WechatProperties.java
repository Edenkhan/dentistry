package com.youruan.dentistry.core.frontdesk;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
@Getter
@Setter
public class WechatProperties {
    private String domain;
    private String token;
    private String appId;
    private String appSecret;
}
