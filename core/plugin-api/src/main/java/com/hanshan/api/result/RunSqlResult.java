package com.hanshan.api.result;

import com.hanshan.api.model.ColumnMeta;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunSqlResult<T> extends Result<T> {

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
    private List<ColumnMeta> fields;

    /**
     * 此处的列是从fields转换过来的，
     * 或者从数据库查询来的
     */
    private List<ColumnMeta> columns;


}
