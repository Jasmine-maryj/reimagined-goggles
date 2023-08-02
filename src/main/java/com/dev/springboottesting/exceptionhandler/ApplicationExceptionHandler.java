package com.dev.springboottesting.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> invalidArgumentException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errorMap = new HashMap<>();
        methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> userNotFoundException(UserNotFoundException userNotFoundException){
        Map<String, String> msg = new HashMap<>();
        msg.put("message", userNotFoundException.getMessage());
        return msg;
    }
}
