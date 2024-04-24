package com.hanshan.common.types;


public enum ResponseEnum {
    //-1 no server info
    NO_SERVER(-1,"no server info message"),
    //200 success
    SUCCESS(200,"success"),
    // 500 unknown error
    UNKNOWN_ERROR(500,"unknown error"),
    //501 内部错误
    PARAM_ERROR(501,"参数错误，检查代码");
    public final Integer code;
    public final String message;


    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
