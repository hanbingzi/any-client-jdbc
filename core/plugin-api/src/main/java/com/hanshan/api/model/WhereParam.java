package com.hanshan.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhereParam {

    private String columnKey;
    /**
     * @Link com.JDBCJavaTypes
     */
    private String columnType;
    private String whereValue;
    // '=' | 'like' | '>' | '<' | 'in' | 'not in' | '<>' | 'isNull' | 'isEmpty';
    private String whereType;
}
