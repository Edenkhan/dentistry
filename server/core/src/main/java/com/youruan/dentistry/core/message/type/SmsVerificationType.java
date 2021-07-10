package com.youruan.dentistry.core.message.type;

import java.util.Date;

public interface SmsVerificationType {

    /**
     * 获取短信内容，如果使用模板 ID，则为模板变量
     *
     * @param code 验证码
     * @return 短信内容或模板变量
     */
    String getContent(String code);

    /**
     * 验证短信类型
     *
     * @return 类型
     */
    Integer getType();

    /**
     * 获取过期时间
     *
     * @param now 当前时间
     * @return 过期时间
     */
    Date getExpirationDate(Date now);

    /**
     * 如果使用模板 ID，则返回模板 ID
     *
     * @return 模板 ID
     */
    String getTemplateId();


}
