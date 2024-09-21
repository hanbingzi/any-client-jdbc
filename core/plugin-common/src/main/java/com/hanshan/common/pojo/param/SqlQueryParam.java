package com.hanshan.common.pojo.param;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.query.SqlQuery;
import lombok.Data;

import java.util.List;

@Data
public class SqlQueryParam {
    private Boolean isSingleTable = false;
    private String serverType;
    private String db;
    private String schema;
    private String table;
    private String sql;
    private Boolean alyColumn;
    private List<SqlPsParam> params;
    private IJdbcConfiguration config;

    public static SqlQueryParam getInstance(boolean isSingleTable, SqlQuery query, String serverType, String db, String schema, IJdbcConfiguration config) {
        SqlQueryParam param = new SqlQueryParam();
        param.setSql(query.getSql());
        param.setIsSingleTable(isSingleTable);
        param.setTable(query.getTable());
        param.setParams(query.getParams());
        param.setDb(db);
        param.setAlyColumn(query.getAlyColumn() != null && query.getAlyColumn());
        param.setSchema(schema);
        param.setConfig(config);
        param.setServerType(serverType);
        return param;
    }
}
