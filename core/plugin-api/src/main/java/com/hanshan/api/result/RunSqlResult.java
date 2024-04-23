package com.hanshan.api.result;

import lombok.Data;

@Data
public class RunSqlResult<T, S> extends QueryResult<T> {

    /**
     * 执行的sql
     */
    private String sql;
    /**
     * 影响的行
     */
    private Integer affectedRows;
    /**
     * 是否查询
     */
    private Boolean isQuery;
    /**
     * sql查询的表格结构
     */
    private S[] fields;
    /**
     * 此处的列是从fields转换过来的，
     */
    private String[] columns;
}
