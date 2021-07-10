package com.youruan.dentistry.core.frontdesk.service;

public interface AccessService {
    /**
     * 对接微信公众号
     */
    String access(String signature, String timestamp, String nonce, String echostr);
}
