package com.hanshan.api.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class QueryResult<T> extends Result<T>{
    /**
     * 耗费的时间
     */

    private Integer costTime;


}
