package com.hanshan.api.result;

import com.hanshan.common.types.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Boolean success;
    /**
     * 错误编码
     */
    private Integer code;
    /**
     * 1.成功时，需要展示的信息
     * 2.不成功的时候，错误的信息
     */
    private String message;

    /**
     * 暂时未解析的错误信息
     */
    private Object error;
    /**
     * 数据结果
     */
    private T data;



    public static Result<Object> success() {
        return Result.builder().success(true).code(ResponseEnum.SUCCESS.code).build();
    }

    public static <R> Result<R> success(R data) {
        Result<R> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResponseEnum.SUCCESS.code);
        result.setMessage(ResponseEnum.SUCCESS.message);
        result.setData(data);
        return result;
    }


    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(ResponseEnum response) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(response.code);
        result.setMessage(response.message);
        return result;
    }

    public static <T,S> Result<T> error(Result<S> response) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(response.code);
        result.setMessage(response.message);
        return result;
    }
}
