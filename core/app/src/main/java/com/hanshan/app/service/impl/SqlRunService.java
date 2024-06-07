package com.hanshan.app.service.impl;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.query.BatchSqlQuery;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.query.SqlQuery;
import com.hanshan.common.pojo.result.RunSqlResult;
import com.hanshan.sqlbase.SqlExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SqlRunService {

    @Autowired
    private SqlConfigService sqlConfigService;
    //纯sql执行
    //执行插入和更改，有参数
    //执行查询，有参数

    public RunSqlResult<List<Map<String, Object>>> query(ConnectQuery connectQuery, SqlQuery sqlQuery) throws Exception {

        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlExecutor.query(connectQuery, jdbcConfigurationApi, sqlQuery,false);
    }

    /**
     * table query 会保证db schema table都是正确的，而query可能只有一条sql
     * @param connectQuery
     * @param sqlQuery
     * @return
     */
    public RunSqlResult<List<Map<String, Object>>> tableQuery(ConnectQuery connectQuery, SqlQuery sqlQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        sqlQuery.setAlyColumn(true);
        return SqlExecutor.query(connectQuery, jdbcConfigurationApi, sqlQuery,true);
    }

    public RunSqlResult exec(ConnectQuery connectQuery, SqlQuery sqlQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlExecutor.execute(connectQuery, jdbcConfigurationApi, sqlQuery.getSql(), sqlQuery.getParams());
    }

    public List<RunSqlResult> multiExec(ConnectQuery connectQuery, BatchSqlQuery batchSqlQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlExecutor.multiExecute(connectQuery, jdbcConfigurationApi, batchSqlQuery.getBatchSql(), batchSqlQuery.getParams());
    }

    public RunSqlResult runSql(ConnectQuery connectQuery, String sql) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlExecutor.runSql(connectQuery, jdbcConfigurationApi, sql);
    }

    public List<RunSqlResult> runBatch(ConnectQuery connectQuery, BatchSqlQuery batchQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlExecutor.runBatch(connectQuery, jdbcConfigurationApi, batchQuery);
    }


}
