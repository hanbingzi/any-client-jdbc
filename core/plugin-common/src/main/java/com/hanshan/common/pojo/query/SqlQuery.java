package com.hanshan.common.pojo.query;

import com.hanshan.common.pojo.param.SqlPsParam;
import lombok.Data;

import java.util.List;

@Data
public class SqlQuery{

    /**
     * 是否是单表查询，单表查询会返回字段的比较详细的信息
     */
    private String table;
    private String sql;
    //详细的分析查询的column
    private Boolean alyColumn;
    private List<SqlPsParam> params;

}
