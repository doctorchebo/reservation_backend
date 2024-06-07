package com.marcelo.reservation.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        Map<String, List<String>> fieldErrors = new LinkedHashMap<>();
        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            fieldErrors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>()).add(fieldError.getDefaultMessage());
        }

        responseBody.put("timestamp", new Date());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("errors", fieldErrors);
        responseBody.put("path", request.getContextPath());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(
            {
                    MethodArgumentTypeMismatchException.class,
                    NotFoundException.class,
                    ConstraintViolationException.class,
                    NotValidDateException.class,
            })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handlePathVariableValidationException(Exception ex, HttpServletRequest request){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", ex.getMessage());
        responseBody.put("path", request.getServletPath());

        return responseBody;
    }
}
