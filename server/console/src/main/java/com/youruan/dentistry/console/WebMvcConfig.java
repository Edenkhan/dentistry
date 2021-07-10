package com.youruan.dentistry.console;

import com.youruan.dentistry.console.base.interceptor.SessionInterceptor;
import com.youruan.dentistry.console.base.resolver.HandlerSessionArgumentResolver;
import com.youruan.dentistry.core.platform.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final EmployeeService employeeService;

    public WebMvcConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor(employeeService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerSessionArgumentResolver(employeeService));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+"c:/image/");
    }
}
