package com.youruan.dentistry.core.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationUtils {

    /**
     * 判断是否有效的密码。密码长度应当为 6 ~ 16 位。
     *
     * @param password 密码
     * @return 是否有效的密码
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 16;
    }

    /**
     * 判断是否有效的手机号。手机号以 1 开头。
     *
     * @param phoneNumber 手机号
     * @return 是否有效的手机号
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 11 && phoneNumber.matches("1\\d{10}");
    }

    /**
     * 判断是否有效的昵称。
     *
     * @param nickname 昵称
     * @return 是否有效的昵称。
     */
    public static boolean isValidNickname(String nickname) {
        return nickname.length() < 20;
    }


    /**
     * 判断是否有效的身份证号。
     *
     * @return 是否有效的身份证号
     */
    public static boolean isValidIdCardNumber(String idCardNumber) {
        if (StringUtils.isBlank(idCardNumber)) {
            return false;
        }
        if (!idCardNumber.matches("\\d{17}[0-9X]")) {
            return false;
        }

        String birthday = idCardNumber.substring(6, 14);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(birthday);
        } catch (ParseException e) {
            return false;
        }

        int[] factor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int seventeenSum = 0;
        for (int i = 0; i < 17; i++) {
            seventeenSum += Character.getNumericValue(idCardNumber.charAt(i)) * factor[i];
        }
        return "10X98765432".toCharArray()[seventeenSum % 11] == idCardNumber.charAt(17);
    }

    /**
     * 判断是否有效的经度值
     *
     * @param longitude 经度
     * @return 是否有效的经度值
     */
    public static boolean isValidLongitude(BigDecimal longitude) {
        return longitude.compareTo(new BigDecimal(-180)) > 0
                && longitude.compareTo(new BigDecimal(180)) < 0;
    }

    /**
     * 判断是否有效的纬度值
     *
     * @param latitude 纬度
     * @return 是否有效的纬度
     */
    public static boolean isValidLatitude(BigDecimal latitude) {
        return latitude.compareTo(new BigDecimal(-90)) > 0
                && latitude.compareTo(new BigDecimal(90)) < 0;
    }
}
