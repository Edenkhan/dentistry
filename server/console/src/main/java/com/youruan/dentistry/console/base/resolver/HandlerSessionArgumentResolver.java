package com.youruan.dentistry.console.base.resolver;

import com.youruan.dentistry.console.base.utils.SessionUtils;
import com.youruan.dentistry.core.platform.domain.Employee;
import com.youruan.dentistry.core.platform.service.EmployeeService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class HandlerSessionArgumentResolver implements HandlerMethodArgumentResolver {

    private EmployeeService employeeService;

    public HandlerSessionArgumentResolver(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == Employee.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        String username = SessionUtils.getAuthenticated();
        if (username == null) {
            return null;
        }
        return employeeService.getByUsername(username);
    }
}
