package com.youruan.dentistry.core.message.exception;

/**
 * 短信验证码发送繁忙
 */
public class SmsVerificationSendBusyException extends RuntimeException {

    /**
     * 下一次可发送时间
     */
    private final long nextTime;

    public SmsVerificationSendBusyException(String message) {
        this(message, -1);
    }

    public SmsVerificationSendBusyException(String message, long nextTime) {
        super(message);
        this.nextTime = nextTime;
    }

    public long getNextTime() {
        return nextTime;
    }
}
