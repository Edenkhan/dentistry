package com.youruan.dentistry.portal;

import com.youruan.dentistry.core.base.storage.DiskFileStorageProperties;
import com.youruan.dentistry.core.user.service.RegisteredUserService;
import com.youruan.dentistry.portal.base.interceptor.SessionInterceptor;
import com.youruan.dentistry.portal.base.resolver.HandlerSessionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RegisteredUserService registeredUserService;
    private final DiskFileStorageProperties diskFileStorageProperties;


   public WebMvcConfig(RegisteredUserService registeredUserService,
                        DiskFileStorageProperties diskFileStorageProperties) {
        this.registeredUserService = registeredUserService;
        this.diskFileStorageProperties = diskFileStorageProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerSessionArgumentResolver(registeredUserService));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + diskFileStorageProperties.getBaseDirectory() + "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .maxAge(3600);
    }


}
