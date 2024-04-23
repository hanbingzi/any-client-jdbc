package com.hanshan.api.result;

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
        return Result.builder().success(true).build();
    }

    public static <R> Result<R> success(R data) {
        Result<R> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result<Object> error(Integer code, String message) {
        Result<Object> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
