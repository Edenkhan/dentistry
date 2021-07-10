package main.lending;

import main.AbstractEntities;
import mate.Const;
import mate.ConstGroup;
import mate.Entity;
import mate.Property;

import java.util.Date;

public class MessageEntities extends AbstractEntities {

    public MessageEntities() {
        Entity SmsMessage = SmsMessage();
        addEntity(SmsMessage);
        addEntity(SmsVerification(SmsMessage));
    }

    private Entity SmsMessage() {
        Entity entity = new Entity("SmsMessage", "smsm", "短信发送记录");
        entity.setProperties(
                new Property("String", "phoneNumber", "手机号").setSearchable(true).setIndex(true),
                new Property("String", "templateId", "模板"),
                new Property("String", "content", "短信内容"),
                new Property(Date.class, "closeDate", "结束时间").setUpdate(true),
                new Property("String", "note", "备注").setUpdate(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_STATE, "state", "状态",
                        new Const("PENDING", "待发送"),
                        new Const("SUCCESS", "发送成功"),
                        new Const("FAILED", "发送失败")).setIndex(true).setUpdate(true));
        return entity;
    }

    private Entity SmsVerification(Entity SmsMessage) {
        Entity entity = new Entity("SmsVerification", "smsv", "短信检验");
        entity.setProperties(
                new Property("Long", "smsMessageId", "短信发送记录").setIndex(true).setJoin(SmsMessage, "id", "content"),
                new Property("String", "phoneNumber", "手机号").setSearchable(true).setIndex(true),
                new Property("String", "code", "校验码"),
                new Property("String", "requestIp", "请求 IP").setIndex(true).setSearchable(true),
                new Property("Integer", "retryCount", "重试次数").setUpdate(true),
                new Property(Date.class, "expirationDate", "过期时间").setLikeSearchable(true));
        entity.setConstGroups(
                new ConstGroup(ConstGroup.METHOD_STATE, "state", "状态",
                        new Const("PENDING", "待验证"),
                        new Const("SUCCESS", "已验证"),
                        new Const("REVOKE", "已无效")).setIndex(true).setUpdate(true),
                new ConstGroup(ConstGroup.METHOD_TYPE, "type", "类型",
                        new Const("LOGIN", "登录验证码"),
                        new Const("BORROW_REQUEST", "借款申请")).setIndex(true));
        return entity;
    }

    @Override
    public String getModuleName() {
        return "message";
    }
}
