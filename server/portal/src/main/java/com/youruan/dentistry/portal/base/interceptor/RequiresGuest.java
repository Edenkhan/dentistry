package com.youruan.dentistry.portal.base.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {TYPE, METHOD})
@Retention(value = RUNTIME)
public @interface RequiresGuest {
}
