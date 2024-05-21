package com.hanshan.common.pojo.model;

import com.hanshan.common.types.JDBCJavaTypes;
import lombok.Data;

@Data
public class ColumnMeta {

    private String name;
    private String label;
    private String columnType;
    private Integer columnTypeId;
    //java 统一类型
    private String dataType;
    private String columnDefinition;
    private Integer length;
    private Integer scale;
    //最大长度，暂时无用
    private String maxLength;
    private String nullable;
    private Object defaultValue;
    private String comment;
    private String tableName;
    private String schemaName;
    private String dbName;
    private String key;
    private String autoIncrement;
    //extra?: any;
    private Boolean isNotNull;
    private Boolean isUnique;
    private Boolean isPrimary;
    private String pk;
    private Boolean isAutoIncrement;


    private JDBCJavaTypes jdbcDataType;

}
