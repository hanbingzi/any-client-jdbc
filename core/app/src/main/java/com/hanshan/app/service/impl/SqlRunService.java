package com.hanshan.app.service.impl;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.query.SqlQuery;
import com.hanshan.api.result.RunSqlResult;
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

    public RunSqlResult<List<Map<String, Object>>> query(ConnectQuery connectQuery, SqlQuery sqlQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlExecutor.query(connectQuery, jdbcConfigurationApi, sqlQuery.getSql(), sqlQuery.getParams());
    }


}
