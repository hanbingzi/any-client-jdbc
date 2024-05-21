package com.hanshan.common.pojo.query;

import com.hanshan.common.pojo.param.SqlPsParam;
import lombok.Data;

import java.util.List;

@Data
public class BatchSqlQuery {

    private List<String> batchSql;
    private List<List<SqlPsParam>> params;
    private Boolean isTransaction;
    private Boolean alyColumn;

}
