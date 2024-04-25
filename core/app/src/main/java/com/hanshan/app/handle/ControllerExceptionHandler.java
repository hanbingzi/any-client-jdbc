package com.hanshan.app.handle;

import com.hanshan.api.result.Result;
import com.hanshan.app.exception.NoServerException;
import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.common.types.ResponseEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 校验的统一异常处理
     */
    @ExceptionHandler(value = {NoServerException.class})
    @ResponseBody
    public Result noServerExceptionHandler() {
        return Result.error(ResponseEnum.NO_SERVER);
    }

    @ExceptionHandler(value = {ParamErrorException.class})
    @ResponseBody
    public Result paramErrorExceptionHandler() {
        return Result.error(ResponseEnum.PARAM_ERROR);
    }


}
