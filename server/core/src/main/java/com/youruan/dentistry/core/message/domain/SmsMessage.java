
package com.youruan.dentistry.core.message.domain;

import java.util.Date;
import com.youruan.dentistry.core.base.domain.BasicDomain;


/**
 * 短信发送记录
 * 
 */
public class SmsMessage
    extends BasicDomain
{

    private final static long serialVersionUID = 1L;
    /**
     * 状态
     * 
     */
    protected Integer state;
    /**
     * 状态：「待发送」
     * 
     */
    public final static int STATE_PENDING = 0;
    /**
     * 状态：「发送成功」
     * 
     */
    public final static int STATE_SUCCESS = (STATE_PENDING + 1);
    /**
     * 状态：「发送失败」
     * 
     */
    public final static int STATE_FAILED = (STATE_SUCCESS + 1);
    /**
     * 手机号
     * 
     */
    protected String phoneNumber;
    /**
     * 模板
     * 
     */
    protected String templateId;
    /**
     * 短信内容
     * 
     */
    protected String content;
    /**
     * 结束时间
     * 
     */
    protected Date closeDate;
    /**
     * 备注
     * 
     */
    protected String note;

    /**
     * 设置状态
     * 
     * @param state
     *     状态
     */
    public SmsMessage setState(Integer state) {
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
     * 状态是否为：「待发送」
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
     * 设置状态为：「待发送」
     * 
     */
    public SmsMessage setPendingState() {
        this.state = STATE_PENDING;
        return this;
    }

    /**
     * 状态是否为：「发送成功」
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
     * 设置状态为：「发送成功」
     * 
     */
    public SmsMessage setSuccessState() {
        this.state = STATE_SUCCESS;
        return this;
    }

    /**
     * 状态是否为：「发送失败」
     * 
     * @return
     *     是，为 true；不是，为 false。
     */
    public Boolean isFailedState() {
        if (this.state == null) {
            return null;
        }
        return (this.state == STATE_FAILED);
    }

    /**
     * 设置状态为：「发送失败」
     * 
     */
    public SmsMessage setFailedState() {
        this.state = STATE_FAILED;
        return this;
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
     * 返回模板
     * 
     * @return
     *     模板
     */
    public String getTemplateId() {
        return this.templateId;
    }

    /**
     * 设置模板
     * 
     * @param templateId
     *     模板
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 返回短信内容
     * 
     * @return
     *     短信内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置短信内容
     * 
     * @param content
     *     短信内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 返回结束时间
     * 
     * @return
     *     结束时间
     */
    public Date getCloseDate() {
        return this.closeDate;
    }

    /**
     * 设置结束时间
     * 
     * @param closeDate
     *     结束时间
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * 返回备注
     * 
     * @return
     *     备注
     */
    public String getNote() {
        return this.note;
    }

    /**
     * 设置备注
     * 
     * @param note
     *     备注
     */
    public void setNote(String note) {
        this.note = note;
    }

}
