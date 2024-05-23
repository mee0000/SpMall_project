package com.example.spmall.exception;

import com.example.spmall.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 *
 * @author toy aa@nfda
 * @version 2024/04/03 8:44 PM
 * @since JDK11
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage(): "조작실패");
    }
}
