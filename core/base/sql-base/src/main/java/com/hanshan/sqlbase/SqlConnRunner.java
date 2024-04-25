package com.hanshan.sqlbase;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.common.types.ResponseEnum;
import com.hanshan.sqlbase.types.SqlTableEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlConnRunner {
    public final static Logger logger = LoggerFactory.getLogger(SqlConnRunner.class);

    /**
     * 展示所有的库
     *
     * @param connectQuery
     * @param configurationApi
     * @return
     */
    public static Result<List<String>> showDatabase(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi) {
        if (StringUtils.isNotEmpty(connectQuery.getDb()) || StringUtils.isNotEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        Result<Connection> connectResult = DataSourceManager.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getCatalogs();
            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
            return Result.success(databases);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    //展示所有的schema
    public static Result<List<String>> showSchema(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi) {
        if (StringUtils.isEmpty(connectQuery.getDb())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        Result<Connection> connectResult = DataSourceManager.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getSchemas();
            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
            return Result.success(databases);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    /**
     * //展示所有的table
     * //展示所有的view
     * @param connectQuery
     * @param configurationApi
     * @param tableEnum
     * @return
     */
    public static Result<List<String>> showTables(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi, SqlTableEnum tableEnum) {
        if (StringUtils.isEmpty(connectQuery.getDb())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        if (configurationApi.hasSchema() && StringUtils.isEmpty(connectQuery.getSchema())) {
            return Result.error(ResponseEnum.PARAM_ERROR);
        }
        Result<Connection> connectResult = DataSourceManager.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String schema = configurationApi.hasSchema() ? connectQuery.getSchema() : null;
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getTables(connectQuery.getDb(), schema, null, new String[]{tableEnum.name()});
            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
            return Result.success(databases);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    //展示所有的function
    //展示所有的trigger
    //展示所有的sequence
    //纯sql执行
    //执行插入和更改，无参数
    //执行插入和更改，有参数
    //执行查询，无参数
    //执行查询，有参数

}
