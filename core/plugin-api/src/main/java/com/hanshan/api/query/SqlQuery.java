package com.hanshan.api.query;

import com.hanshan.api.param.SqlPsParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class SqlQuery{

    private String sql;
    private List<SqlPsParam> params;

}
