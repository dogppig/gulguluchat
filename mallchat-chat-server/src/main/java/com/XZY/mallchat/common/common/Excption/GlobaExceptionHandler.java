package com.XZY.mallchat.common.common.Excption;

import com.XZY.mallchat.common.user.domain.vo.resp.ApiResult;
import com.abin.frequencycontrol.exception.BusinessException;
import com.abin.frequencycontrol.exception.CommonErrorEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description:全局异常处理器
 * Author:戏中言
 *
 * @date: 2024/4/2 15:35
 */
@RestControllerAdvice
@Slf4j
public class GlobaExceptionHandler {
    /**
     * 最后一道防线
     * 处理全部异常（顶级异常）
     */
    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?>throwable (Throwable e){
        log.error("system erro!The Reason is: {},{}",e.getMessage(),e.getStackTrace());
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }

    /**
     * 处理参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> MethodArgumentNotValidException(MethodArgumentNotValidException e){
        StringBuilder errorMGS = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(x->errorMGS.append(x.getField()).append(",").append(x.getDefaultMessage()).append(","));
        String message = errorMGS.toString();
        return ApiResult.fail(CommonErrorEnum.PARAM_VALID.getErrorCode(), message.substring(0,errorMGS.length()-1));
    }

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessException(BusinessException e){
        log.error("business exception!The reason is:{},{}",e.getMessage(),e.getStackTrace());
        return ApiResult.fail(e.getErrorCode(),e.getErrorMsg());
    }




}
