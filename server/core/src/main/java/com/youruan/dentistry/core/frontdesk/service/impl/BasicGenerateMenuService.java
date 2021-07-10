package com.youruan.dentistry.core.frontdesk.service.impl;

import com.alibaba.fastjson.JSON;
import com.youruan.dentistry.core.base.constant.WechatConstant;
import com.youruan.dentistry.core.base.utils.HttpClientUtils;
import com.youruan.dentistry.core.frontdesk.WechatProperties;
import com.youruan.dentistry.core.frontdesk.domain.wxmenu.OneMenu;
import com.youruan.dentistry.core.frontdesk.domain.wxmenu.ViewButton;
import com.youruan.dentistry.core.frontdesk.domain.wxmenu.WxMenu;
import com.youruan.dentistry.core.frontdesk.service.GenerateMenuService;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

@Service
public class BasicGenerateMenuService implements GenerateMenuService {

    private final WechatProperties wechatProperties;

    public BasicGenerateMenuService(WechatProperties wechatProperties) {
        this.wechatProperties = wechatProperties;
    }

    @Override
    public void define() {

        try {
            //获取响应内容
            String json = HttpClientUtils.doGet(String.format(WechatConstant.ACCESS_TOKEN_URL,wechatProperties.getAppId(),wechatProperties.getAppSecret()));
            Map<String, String> resultMap = JSON.parseObject(json, Map.class);
            //定义click类型菜单
            /*ClickButton clickButton = new ClickButton();
            clickButton.setType("click");
            clickButton.setName("测试click");
            clickButton.setKey("rselfmenu_0_0");*/
            //定义view类型菜单
            ViewButton viewButton = new ViewButton();
            viewButton.setType("view");
            viewButton.setName("授权登录");
            String encodeUrl = URLEncoder.encode(wechatProperties.getDomain() + WechatConstant.REDIRECT_URI, "UTF-8");
            viewButton.setUrl(String.format(WechatConstant.BASIC_OAUTH_URL,wechatProperties.getAppId(),encodeUrl));
            //定义1级菜单
            OneMenu oneMenu = new OneMenu();
            oneMenu.setName("法赫光学");
            oneMenu.setSub_button(Arrays.asList(/*clickButton,*/viewButton));
            //定义微信公众号菜单
            WxMenu wxMenu = new WxMenu();
            wxMenu.setButton(Arrays.asList(oneMenu));
            HttpClientUtils.doPostJson(WechatConstant.CREATE_MENU_URL+resultMap.get("access_token"),JSON.toJSONString(wxMenu));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
