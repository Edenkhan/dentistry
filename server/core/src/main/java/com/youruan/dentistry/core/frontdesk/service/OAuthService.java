package com.youruan.dentistry.core.frontdesk.service;


import com.youruan.dentistry.core.frontdesk.domain.wxoauth.UserLogin;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.WxUserInfo;

public interface OAuthService {

    /**
     * 用户授权，回调地址
     */
    WxUserInfo callback(String code, String state);

    /**
     * 微信用户注册
     * 返回用户id
     */
    UserLogin register(WxUserInfo wxUserInfo);
}
