package com.mall.admin.common.exception;

import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * GlobalControllerException
 *
 * @author youfu.wang
 * @date 2023/10/7 9:47
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerException {
    /**
     * 参数检查异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult handleException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 未知异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult otherException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(e.getMessage());
    }
}
