package com.youruan.dentistry.core.frontdesk.service.impl;

import com.youruan.dentistry.core.base.utils.CommonUtil;
import com.youruan.dentistry.core.frontdesk.WechatProperties;
import com.youruan.dentistry.core.frontdesk.service.AccessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicAccessService implements AccessService {

    private final WechatProperties wechatProperties;

    public BasicAccessService(WechatProperties wechatProperties) {
        this.wechatProperties = wechatProperties;
    }

    @Override
    public String access(String signature, String timestamp, String nonce, String echostr) {
        return checkSignature(signature,timestamp,nonce) ? echostr : null;
    }

    /**
     * 验证微信签名
     */
    private boolean checkSignature(String signature,String timestamp,String nonce){
        List<String> strList = CommonUtil.dictSort(wechatProperties.getToken(), timestamp, nonce);
        String s = CommonUtil.listToString(strList);
        String finalStr = CommonUtil.sha1(s, "utf-8");
        return signature.equals(finalStr);
    }
}
