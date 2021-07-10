package com.youruan.dentistry.core.frontdesk.service.impl;

import com.alibaba.fastjson.JSON;
import com.youruan.dentistry.core.base.constant.WechatConstant;
import com.youruan.dentistry.core.base.utils.HttpClientUtils;
import com.youruan.dentistry.core.frontdesk.WechatProperties;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.UserLogin;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.WxResult;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.WxUserInfo;
import com.youruan.dentistry.core.frontdesk.service.OAuthService;
import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.query.RegisteredUserQuery;
import com.youruan.dentistry.core.user.service.RegisteredUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class BasicOAuthService implements OAuthService {

    private final RegisteredUserService registeredUserService;
    private final WechatProperties wechatProperties;

    public BasicOAuthService(RegisteredUserService registeredUserService, WechatProperties wechatProperties) {
        this.registeredUserService = registeredUserService;
        this.wechatProperties = wechatProperties;
    }

    @Override
    public WxUserInfo callback(String code, String state) {
        if(state==null || !state.equals("GHOST")) {
            throw new RuntimeException("回調錯誤");
        }
        String json = HttpClientUtils.doGet(String.format(WechatConstant.BASIC_OAUTH_ACCESS_TOKEN_URL,wechatProperties.getAppId(),wechatProperties.getAppSecret(),code));
        WxResult wxResult = JSON.parseObject(json, WxResult.class);
        return getWxUserInfo(wxResult.getAccessToken(),wxResult.getOpenid());
    }

    @Override
    public UserLogin register(WxUserInfo wxUserInfo) {
        Assert.notNull(wxUserInfo,"微信没获取到用户信息");
        Assert.notNull(wxUserInfo.getOpenid(),"微信重复回调，openid=null");
        RegisteredUserQuery qo = new RegisteredUserQuery();
        qo.setOpenid(wxUserInfo.getOpenid());
        RegisteredUser registeredUser = registeredUserService.queryOne(qo);
        if(registeredUser == null) registeredUser = registeredUserService.create(wxUserInfo);
        UserLogin userLogin = new UserLogin();
        userLogin.setId(registeredUser.getId());
        // 判断当前用户是否被管理员锁定
        userLogin.setLocked(registeredUser.getLocked() != null && registeredUser.getLocked());
        return userLogin;
    }


    /**
     * 通过access_token和openid拉取用户信息
     */
    private WxUserInfo getWxUserInfo(String accessToken, String openId) {
        String json = HttpClientUtils.doGet(String.format(WechatConstant.BASIC_GET_USERINFO_URL, accessToken, openId));
        return JSON.parseObject(json, WxUserInfo.class);
    }


}
