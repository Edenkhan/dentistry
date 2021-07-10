package com.youruan.dentistry.core.base.aliyunsms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@ConditionalOnProperty(prefix = "aliyun.sms", name = "enable")
public class AliyunSms {

    private final AliyunSmsProperties aliyunSmsProperties;

    private ObjectMapper objectMapper;
    private IAcsClient acsClient;

    public AliyunSms(AliyunSmsProperties aliyunSmsProperties) {
        this.aliyunSmsProperties = aliyunSmsProperties;
    }

    @PostConstruct
    public void init() {
        acsClient = new DefaultAcsClient(DefaultProfile.getProfile("cn-hangzhou", aliyunSmsProperties.getAccessKeyId(), aliyunSmsProperties.getAccessKeySecret()));
        objectMapper = new ObjectMapper();
    }

    public void sendSms(String outId, String phoneNumber, String templateCode, String templateParam) {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", aliyunSmsProperties.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        request.putQueryParameter("OutId", outId);

        SendSmsResponse response;
        try {
            CommonResponse commonResponse = acsClient.getCommonResponse(request);
            response = objectMapper.readValue(commonResponse.getData(), SendSmsResponse.class);
        } catch (ClientException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        String code = response.getCode();
        if (code == null) {
            throw new RuntimeException("短信发送失败，没有响应 code");
        }

        if (!code.equals("OK")) {
            throw new RuntimeException("短信发送失败，错误 Code：" + code + "，错误消息：" + response.getMessage());
        }
    }

}
