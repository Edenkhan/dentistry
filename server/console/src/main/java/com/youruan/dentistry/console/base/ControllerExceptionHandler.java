package com.youruan.dentistry.console.base;

import com.youruan.dentistry.console.base.exception.UnauthenticatedException;
import com.youruan.dentistry.console.base.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<?> handleAuthorizationException(UnauthenticatedException e) {
        return ErrorResponseEntity.unauthorized("请先进行登录");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        return ErrorResponseEntity.forbidden("没有权限访问");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOG.warn("不支持的 HTTP 请求方法 {}", e.getMethod());
        return ErrorResponseEntity.notFound("请求的资源不存在");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        LOG.error(e.getMessage(), e);
        return ErrorResponseEntity.badRequest(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        String message = fieldError == null ? "参数有误" : fieldError.getDefaultMessage();
        return ErrorResponseEntity.badRequest(message);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException e) {
        LOG.error("网络出现异常", e);
        return ErrorResponseEntity.badRequest("网络出现异常");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError == null ? "参数有误" : fieldError.getDefaultMessage();
        return ErrorResponseEntity.badRequest(message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ErrorResponseEntity.badRequest("缺少 " + e.getParameterName() + " 参数");
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleBindException(Exception e) {
        e.printStackTrace();
        return ErrorResponseEntity.internalServerError("系统繁忙，请稍后再试");
    }
}
