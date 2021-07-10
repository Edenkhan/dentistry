
package com.youruan.dentistry.core.message.domain;

import java.util.Date;
import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 短信检验
 * 
 */
public class SmsVerification
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 状态
     * 
     */
    protected Integer state;
    /**
     * 状态：「待验证」
     * 
     */
    public final static int STATE_PENDING = 0;
    /**
     * 状态：「已验证」
     * 
     */
    public final static int STATE_SUCCESS = (STATE_PENDING + 1);
    /**
     * 状态：「已无效」
     * 
     */
    public final static int STATE_REVOKE = (STATE_SUCCESS + 1);
    /**
     * 类型
     * 
     */
    protected Integer type;
    /**
     * 类型：「登录验证码」
     * 
     */
    public final static int TYPE_LOGIN = 0;
    /**
     * 短信发送记录
     * 
     */
    protected Long smsMessageId;
    /**
     * 手机号
     * 
     */
    protected String phoneNumber;
    /**
     * 校验码
     * 
     */
    protected String code;
    /**
     * 请求 IP
     * 
     */
    protected String requestIp;
    /**
     * 重试次数
     * 
     */
    protected Integer retryCount;
    /**
     * 过期时间
     * 
     */
    protected Date expirationDate;

    /**
     * 设置状态
     * 
     * @param state
     *     状态
     */
    public SmsVerification setState(Integer state) {
        this.state = state;
        return this;
    }

    /**
     * 返回状态
     * 
     * @return
     *     状态
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 状态是否为：「待验证」
     * 
     * @return
     *     是，为 true；不是，为 false。
     */
    public Boolean isPendingState() {
        if (this.state == null) {
            return null;
        }
        return (this.state == STATE_PENDING);
    }

    /**
     * 设置状态为：「待验证」
     * 
     */
    public SmsVerification setPendingState() {
        this.state = STATE_PENDING;
        return this;
    }

    /**
     * 状态是否为：「已验证」
     * 
     * @return
     *     是，为 true；不是，为 false。
     */
    public Boolean isSuccessState() {
        if (this.state == null) {
            return null;
        }
        return (this.state == STATE_SUCCESS);
    }

    /**
     * 设置状态为：「已验证」
     * 
     */
    public SmsVerification setSuccessState() {
        this.state = STATE_SUCCESS;
        return this;
    }

    /**
     * 状态是否为：「已无效」
     * 
     * @return
     *     是，为 true；不是，为 false。
     */
    public Boolean isRevokeState() {
        if (this.state == null) {
            return null;
        }
        return (this.state == STATE_REVOKE);
    }

    /**
     * 设置状态为：「已无效」
     * 
     */
    public SmsVerification setRevokeState() {
        this.state = STATE_REVOKE;
        return this;
    }

    /**
     * 设置类型
     * 
     * @param type
     *     类型
     */
    public SmsVerification setType(Integer type) {
        this.type = type;
        return this;
    }

    /**
     * 返回类型
     * 
     * @return
     *     类型
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 类型是否为：「登录验证码」
     * 
     * @return
     *     是，为 true；不是，为 false。
     */
    public Boolean isLoginType() {
        if (this.type == null) {
            return null;
        }
        return (this.type == TYPE_LOGIN);
    }

    /**
     * 设置类型为：「登录验证码」
     * 
     */
    public SmsVerification setLoginType() {
        this.type = TYPE_LOGIN;
        return this;
    }

    /**
     * 返回短信发送记录
     * 
     * @return
     *     短信发送记录
     */
    public Long getSmsMessageId() {
        return this.smsMessageId;
    }

    /**
     * 设置短信发送记录
     * 
     * @param smsMessageId
     *     短信发送记录
     */
    public void setSmsMessageId(Long smsMessageId) {
        this.smsMessageId = smsMessageId;
    }

    /**
     * 返回手机号
     * 
     * @return
     *     手机号
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * 设置手机号
     * 
     * @param phoneNumber
     *     手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 返回校验码
     * 
     * @return
     *     校验码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置校验码
     * 
     * @param code
     *     校验码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 返回请求 IP
     * 
     * @return
     *     请求 IP
     */
    public String getRequestIp() {
        return this.requestIp;
    }

    /**
     * 设置请求 IP
     * 
     * @param requestIp
     *     请求 IP
     */
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    /**
     * 返回重试次数
     * 
     * @return
     *     重试次数
     */
    public Integer getRetryCount() {
        return this.retryCount;
    }

    /**
     * 设置重试次数
     * 
     * @param retryCount
     *     重试次数
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * 返回过期时间
     * 
     * @return
     *     过期时间
     */
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    /**
     * 设置过期时间
     * 
     * @param expirationDate
     *     过期时间
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
