package com.youruan.dentistry.console.base.interceptor;

import com.youruan.dentistry.console.base.exception.UnauthenticatedException;
import com.youruan.dentistry.console.base.exception.UnauthorizedException;
import com.youruan.dentistry.console.base.utils.SessionUtils;
import com.youruan.dentistry.core.platform.domain.Permission;
import com.youruan.dentistry.core.platform.service.EmployeeService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    private EmployeeService employeeService;

    public SessionInterceptor(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod method = (HandlerMethod) handler;
        boolean hasRequiresGuest = method.hasMethodAnnotation(RequiresGuest.class);
        boolean hasRequiresPermissions = method.hasMethodAnnotation(RequiresPermission.class);
        boolean hasRequiresAuthentication = hasRequiresPermissions || method.hasMethodAnnotation(RequiresAuthentication.class);
        String username = SessionUtils.getAuthenticated();
        if (username == null && hasRequiresAuthentication) {
            throw new UnauthenticatedException();
        }
        if (username != null) {
            if (hasRequiresGuest) {
                throw new UnauthorizedException();
            }
            if (hasRequiresPermissions) {
                RequiresPermission permissionsAnnotation = method.getMethodAnnotation(RequiresPermission.class);
                if (permissionsAnnotation != null) {
                    List<String> permissions = employeeService.listPermissionsByUsername(username).stream().map(Permission::getName).collect(Collectors.toList());
                    String annotationValue = permissionsAnnotation.value();
                    if (!permissions.contains("*") && !permissions.contains(annotationValue)) {
                        throw new UnauthorizedException();
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

}