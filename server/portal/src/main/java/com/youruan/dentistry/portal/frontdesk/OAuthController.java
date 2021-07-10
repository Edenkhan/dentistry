package com.youruan.dentistry.portal.frontdesk;

import com.youruan.dentistry.core.frontdesk.WechatProperties;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.UserLogin;
import com.youruan.dentistry.core.frontdesk.domain.wxoauth.WxUserInfo;
import com.youruan.dentistry.core.frontdesk.service.OAuthService;
import com.youruan.dentistry.portal.base.utils.SessionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oauthService;
    private final HttpServletResponse response;
    private final WechatProperties wechatProperties;

    public OAuthController(OAuthService oauthService, HttpServletResponse response, WechatProperties wechatProperties) {
        this.oauthService = oauthService;
        this.response = response;
        this.wechatProperties = wechatProperties;
    }

    /**
     * 用户授权，回调地址
     */
    @GetMapping("/callback")
    public void callback(String code, String state) throws IOException {
        WxUserInfo wxUserInfo = oauthService.callback(code, state);
        UserLogin userLogin = oauthService.register(wxUserInfo);
        if(!userLogin.getLocked()) {
            SessionUtils.login(userLogin.getId());
        }
        response.sendRedirect(wechatProperties.getDomain());
    }

}
