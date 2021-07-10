package com.youruan.dentistry.portal.base.interceptor;

import com.youruan.dentistry.portal.base.exception.UnauthenticatedException;
import com.youruan.dentistry.portal.base.exception.UnauthorizedException;
import com.youruan.dentistry.portal.base.utils.SessionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod method = (HandlerMethod) handler;
        boolean hasRequiresGuest = method.hasMethodAnnotation(RequiresGuest.class);
        boolean hasRequiresAuthentication = method.hasMethodAnnotation(RequiresAuthentication.class);
        Long userId = SessionUtils.getAuthenticated();
        if (userId == null && hasRequiresAuthentication) {
            throw new UnauthenticatedException();
        }
        if (userId != null) {
            if (hasRequiresGuest) {
                throw new UnauthorizedException();
            }
        }
        return super.preHandle(request, response, handler);
    }


}