package com.youruan.dentistry.portal.base.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    private final static String SESSION_NAME = "user.login.id";
    private final static String SESSION_CODE = "user.login.code";
    private final static String SESSION_SMS_CODE = "user.login.smscode";

    public static Long getAuthenticated() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        return (Long) session.getAttribute(SESSION_NAME);
    }

    public static boolean isAuthenticated() {
        return getAuthenticated() != null;
    }

    public static void login(Long userId) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.setAttribute(SESSION_NAME, userId);
    }

    public static void logout() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.removeAttribute(SESSION_NAME);
    }

    public static void setCode(String code) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.setAttribute(SESSION_CODE,code);
    }

    public static String getCode() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        return (String) session.getAttribute(SESSION_CODE);
    }

    public static boolean hasCode() {
        return getCode() != null;
    }

    public static void setSmsCode(String code) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.setAttribute(SESSION_SMS_CODE,code);
    }

    public static String getSmsCode() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        return (String) session.getAttribute(SESSION_SMS_CODE);
    }

    public static boolean hasSmsCode() {
        return getSmsCode() != null;
    }

}
