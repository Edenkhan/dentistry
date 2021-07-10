package com.youruan.dentistry.core.base.constant;

/**
 * 微信常量类
 */
public class WechatConstant {

    /**
     * 获取access_token的url
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 创建公众号菜单url
     */
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    /**
     * 获取微信用户授权url
     */
    public static final String BASIC_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=GHOST#wechat_redirect";
    /**
     * 用户授权，微信重定向地址
     */
    public static final String REDIRECT_URI = "/api/oauth/callback";
    /**
     * 获取用户授权时access_token的url
     */
    public static final String BASIC_OAUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 获取微信用户信息的url
     */
    public static final String BASIC_GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 微信JSAPI下单URL
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信支付回调地址
     */
    public static final String NOTIFY_URI = "/api/enroll/notify";

}
