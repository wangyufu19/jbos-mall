package com.mall.admin.common.exception;

import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * @param request
     * @param e
     * @return
     */
    public ResponseResult handleException(HttpServletRequest request,
                                          MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 未知异常处理
     * @param e
     * @return
     */
    public ResponseResult otherException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(e.getMessage());
    }
}
