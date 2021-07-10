package com.youruan.dentistry.console.base.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    private final static String SESSION_NAME = "employee.login.username";

    public static String getAuthenticated() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        return (String) session.getAttribute(SESSION_NAME);
    }

    public static boolean isAuthenticated() {
        return getAuthenticated() != null;
    }

    public static void login(String username) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.setAttribute(SESSION_NAME, username);
    }

    public static void logout() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.removeAttribute(SESSION_NAME);
    }

}
