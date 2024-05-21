package com.hanshan.app.model.query;

import com.hanshan.common.pojo.param.SqlPsParam;
import com.hanshan.app.model.Page;
import lombok.Data;

import java.util.List;

@Data
public class TableSqlQuery {

    private String isTable;

    private String table;

    private List<SqlPsParam> params;

    private String sql;

    private Page page;



}
