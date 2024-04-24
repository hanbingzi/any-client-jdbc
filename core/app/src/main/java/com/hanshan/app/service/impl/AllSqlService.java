package com.hanshan.app.service.impl;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.sqlbase.SqlConnRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllSqlService {

    @Autowired
    private SqlConfigService sqlConfigService;

    //展示所有的库

    public Result<Object> showDatabase(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlConnRunner.showDatabase(connectQuery, jdbcConfigurationApi);

    }
    //展示所有的schema
    //展示所有的table
    //展示所有的view
    //展示所有的function
    //展示所有的trigger
    //展示所有的sequence
    //查询分页列表
    //

}
