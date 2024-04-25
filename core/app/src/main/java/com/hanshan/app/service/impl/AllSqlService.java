package com.hanshan.app.service.impl;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.model.NameComment;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.app.model.request.ServerRequestEmpty;
import com.hanshan.sqlbase.ConnectIdKey;
import com.hanshan.sqlbase.DataSourceManager;
import com.hanshan.sqlbase.SqlConnRunner;
import com.hanshan.sqlbase.types.SqlTableEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AllSqlService {

    @Autowired
    private SqlConfigService sqlConfigService;

   public Result testConnect( ConnectQuery connectQuery){
       IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
       return SqlConnRunner.testConnect(connectQuery,jdbcConfigurationApi);
   }
   public Result closeConnect(ConnectQuery connectQuery){
       String idKey = ConnectIdKey.getConnectIdKey(connectQuery);
       DataSourceManager.removeConnection(idKey);

       return Result.success();

   }

    //展示所有的库

    public Result<List<String>> showDatabase(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlConnRunner.showDatabase(connectQuery, jdbcConfigurationApi);
    }
    //展示所有的schema
    public Result<List<String>> showSchema(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlConnRunner.showDatabase(connectQuery, jdbcConfigurationApi);
    }
    //展示所有的table
    public Result<List<NameComment>> showTables(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlConnRunner.showTables(connectQuery, jdbcConfigurationApi, SqlTableEnum.TABLE);
    }
    //展示所有的view
    public Result<List<NameComment>> showViews(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlConnRunner.showTables(connectQuery, jdbcConfigurationApi,SqlTableEnum.VIEW);
    }
    //展示所有的function
    //展示所有的trigger
    //展示所有的sequence
    //查询分页列表
    //

}
