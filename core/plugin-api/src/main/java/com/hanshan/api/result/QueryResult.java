package com.hanshan.api.result;

import lombok.Data;

@Data
public class QueryResult<T> extends Result<T> {
    /**
     * 耗费的时间
     */

    private Integer costTime;


}
