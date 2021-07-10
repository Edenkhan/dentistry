package com.youruan.dentistry.portal.base.resolver;


import com.youruan.dentistry.core.user.domain.RegisteredUser;
import com.youruan.dentistry.core.user.service.RegisteredUserService;
import com.youruan.dentistry.portal.base.utils.SessionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 * @author __
 */
@Component
public class HandlerSessionArgumentResolver implements HandlerMethodArgumentResolver {

    private final RegisteredUserService registeredUserService;

    public HandlerSessionArgumentResolver(RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == RegisteredUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        Long userId = SessionUtils.getAuthenticated();
        if (userId == null) {
            return null;
        }
        return registeredUserService.get(userId);
    }
}
