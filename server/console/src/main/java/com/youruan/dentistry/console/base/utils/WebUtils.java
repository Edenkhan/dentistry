package com.youruan.dentistry.console.base.utils;


import com.google.common.net.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        }
        return xForwardedForHeader.split(",")[0].trim();
    }
}
