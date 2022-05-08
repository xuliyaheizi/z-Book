package com.zhulin.commons;

import com.zhulin.bean.JsonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author:ZHULIN
 * @Description: 异常抛出
 */
@Slf4j
@RestControllerAdvice
public class AssertExceptionHandler {

    /**
     * 处理Assert的异常 --断言异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JsonModel handler(IllegalArgumentException e) {
        log.error("Assert异常:-------------->{}", e.getMessage());
        return new JsonModel(404, e.getMessage());
    }

    /**
     * 处理实体校验的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonModel handler(MethodArgumentNotValidException e) {
        log.error("实体捕获异常:-------------->", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return new JsonModel(404, objectError.getDefaultMessage());
    }
}
