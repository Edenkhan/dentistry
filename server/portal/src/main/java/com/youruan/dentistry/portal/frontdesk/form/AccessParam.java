package com.youruan.dentistry.portal.frontdesk.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 对接公众号，微信后台的请求参数
 */
@Setter
@Getter
public class AccessParam {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}

