package com.youruan.dentistry.console.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseEntity {

    private ErrorResponseEntity(String message) {
        this(message, null);
    }

    private ErrorResponseEntity(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    private String message;
    private Object data;

    public static ResponseEntity<?> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseEntity(message));
    }

    public static ResponseEntity<?> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseEntity(message));
    }

    public Object getData() {
        return data;
    }

    public static ResponseEntity<?> internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseEntity(message));
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseEntity<?> badRequest(String message) {
        return ResponseEntity.badRequest().body(new ErrorResponseEntity(message));
    }

    public static ResponseEntity<?> badRequest(String message, Object data) {
        return ResponseEntity.badRequest().body(new ErrorResponseEntity(message, data));
    }

    public static ResponseEntity<?> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseEntity(message));
    }
}
