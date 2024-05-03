package com.hanshan.app.service.impl;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.model.NameComment;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.common.types.ResponseEnum;
import com.hanshan.sqlbase.ConnectIdKey;
import com.hanshan.sqlbase.DataSourceManager;
import com.hanshan.sqlbase.SqlMetaOperator;
import com.hanshan.sqlbase.types.SqlTableEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllSqlBaseService {

    @Autowired
    private SqlConfigService sqlConfigService;

   public Result testConnect( ConnectQuery connectQuery){
       IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
       return SqlMetaOperator.testConnect(connectQuery,jdbcConfigurationApi);
   }
   public Result closeConnect(ConnectQuery connectQuery){
       String idKey = ConnectIdKey.getConnectIdKey(connectQuery);
       DataSourceManager.removeConnection(idKey);

       return Result.success();

   }

    //展示所有的库

    public Result<List<String>> showDatabase(ConnectQuery connectQuery) {

        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlMetaOperator.showDatabase(connectQuery, jdbcConfigurationApi);
    }
    //展示所有的schema
    public Result<List<String>> showSchema(ConnectQuery connectQuery) {

        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlMetaOperator.showSchema(connectQuery, jdbcConfigurationApi);
    }
    //展示所有的table
    public Result<List<NameComment>> showTables(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showTables(connectQuery, jdbcConfigurationApi, SqlTableEnum.TABLE);
    }
    //展示所有的view
    public Result<List<NameComment>> showViews(ConnectQuery connectQuery) {
        IJdbcConfigurationApi jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showTables(connectQuery, jdbcConfigurationApi,SqlTableEnum.VIEW);
    }
    //展示所有的function
    //展示所有的trigger
    //展示所有的sequence
    //查询分页列表
    //

}
