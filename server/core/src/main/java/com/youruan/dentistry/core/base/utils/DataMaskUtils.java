package com.youruan.dentistry.core.base.utils;

public class DataMaskUtils {

    public static String maskIdCardNumber(String idCardNumber) {
        return idCardNumber.substring(0, 6) + "********" + idCardNumber.substring(14);
    }

    public static String maskPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }

    public static String maskRealName(String realName) {
        return realName.charAt(0) + "**";
    }

}
