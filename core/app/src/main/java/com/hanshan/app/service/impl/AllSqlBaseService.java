package com.hanshan.app.service.impl;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.PrimaryInfo;
import com.hanshan.common.pojo.model.VFTSPInfo;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.common.types.ResponseEnum;
import com.hanshan.sqlbase.ConnectIdKey;
import com.hanshan.sqlbase.DataSourceFactory;
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

   public Result testConnect( ConnectQuery connectQuery) throws Exception {
       IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
       return SqlMetaOperator.testConnect(connectQuery,jdbcConfigurationApi);
   }
    public Result closeConnect(ConnectQuery connectQuery){
        String idKey = ConnectIdKey.getConnectIdKey(connectQuery);
        DataSourceFactory.removeConnection(idKey);
        return Result.success();
    }


    public Result closeAllConnect(){
        DataSourceFactory.removeAllConnection();
        return Result.success();

    }

    //展示所有的库

    public Result<List<String>> showDatabase(ConnectQuery connectQuery) throws Exception {

        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlMetaOperator.showDatabase(connectQuery, jdbcConfigurationApi);
    }
    //展示所有的schema
    public Result<List<String>> showSchema(ConnectQuery connectQuery) throws Exception {

        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        return SqlMetaOperator.showSchema(connectQuery, jdbcConfigurationApi);
    }
    //展示所有的table
    public Result<List<VFTSPInfo>> showTables(ConnectQuery connectQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showTables(connectQuery, jdbcConfigurationApi, SqlTableEnum.TABLE);
    }
    //展示所有的view
    public Result<List<VFTSPInfo>> showViews(ConnectQuery connectQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showTables(connectQuery, jdbcConfigurationApi,SqlTableEnum.VIEW);
    }
    /**
     * 展示所有的function
     */
    public Result<List<VFTSPInfo>> showFunctions(ConnectQuery connectQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showFunctions(connectQuery, jdbcConfigurationApi);
    }

    /**
     * 展示所有的存储过程
     * @param connectQuery
     * @return
     */
    public Result<List<VFTSPInfo>> showProduces(ConnectQuery connectQuery) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showProcedures(connectQuery, jdbcConfigurationApi);
    }

    public Result<List<PrimaryInfo>> showPrimary(ConnectQuery connectQuery, String table) throws Exception {
        IJdbcConfiguration jdbcConfigurationApi = sqlConfigService.getServerConfigurationApi(connectQuery.getServer());
        if (jdbcConfigurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        return SqlMetaOperator.showPrimary(connectQuery, jdbcConfigurationApi,table);
    }

    //展示所有的trigger
    //展示所有的sequence
    //查询分页列表
    //

}
