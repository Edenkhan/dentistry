package com.youruan.dentistry.console.base;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class BasicErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<?> error(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            if (statusCode == HttpServletResponse.SC_NOT_FOUND) {
                return ErrorResponseEntity.notFound("请求的资源不存在");
            } else if (statusCode == HttpServletResponse.SC_UNAUTHORIZED) {
                return ErrorResponseEntity.notFound("没有权限访问");
            }
        }
        return ErrorResponseEntity.internalServerError("系统繁忙，请稍后再试");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
