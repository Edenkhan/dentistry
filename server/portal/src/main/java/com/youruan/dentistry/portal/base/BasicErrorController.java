package com.youruan.dentistry.portal.base;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BasicErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH, produces = "application/json;charset=utf-8")
    public ResponseEntity<?> json(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            if (statusCode == HttpServletResponse.SC_NOT_FOUND) {
                return ErrorResponseEntity.notFound("请求的资源不存在");
            }
        }
        return ErrorResponseEntity.internalServerError("系统繁忙，请稍后再试");
    }

    @RequestMapping(value = ERROR_PATH)
    public String page(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            if (statusCode == HttpServletResponse.SC_NOT_FOUND) {
                return "404";
            }
        }
        return "500";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
