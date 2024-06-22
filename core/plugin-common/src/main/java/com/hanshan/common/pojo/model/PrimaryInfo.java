package com.hanshan.common.pojo.model;

import lombok.Data;

@Data
public class PrimaryInfo {
    private String tableName;
    private String columnName;
    private String columnType;
    private Integer ordinal;
    private String constraint;
}
