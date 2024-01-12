package com.mall.admin.common.exception;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
     * @param request
     * @param e
     * @return ResponseResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult handleException(HttpServletRequest request,
                                          MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error("参数检查异常");
    }

    /**
     * 业务异常处理
     *
     * @param e
     * @return ResponseResult
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult businessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error("业务异常：" + e.getMessage());
    }

    /**
     * 未知异常处理
     *
     * @param e
     * @return ResponseResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult otherException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error("未知异常!");
    }
}
