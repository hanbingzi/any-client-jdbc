package com.hanshan.sqlbase;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ServerInfo;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.common.types.ResponseEnum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSourceFactory {
    public final static Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
    private static final Map<String, ConnectionWrapper> aliveConnection = new HashMap<>();

    public static void removeConnection(String idKey) {
        for (String key : aliveConnection.keySet()) {
            if (key.equals(idKey) || key.startsWith(idKey)) {
                end(key);
            }
        }
    }

    public static void end(String idKey) {
        try {
            ConnectionWrapper connectionWrapper = aliveConnection.get(idKey);
            if (connectionWrapper != null) {
                aliveConnection.remove(idKey);
                HikariDataSource dataSource = connectionWrapper.getDataSource();
                dataSource.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public static Result<Connection> getConnection(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        try {
            Result<ConnectionWrapper> connectionWrapperResult = getConnectionWrap(connectQuery, configurationApi);
            if (connectionWrapperResult.getSuccess() && connectionWrapperResult.getData() != null) {
                HikariDataSource dataSource = connectionWrapperResult.getData().getDataSource();
                return Result.success(dataSource.getConnection());
            }
            return Result.error(connectionWrapperResult);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        }

    }

    public static Result<ConnectionWrapper> getConnectionWrap(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        String idKey = ConnectIdKey.getConnectIdKey(connectQuery);
        //ConnectionWrapper connectionWrapper = null;
        if (aliveConnection.containsKey(idKey)) {
            ConnectionWrapper connectionWrapper = aliveConnection.get(idKey);
            return Result.success(connectionWrapper);
        } else {
            JdbcConnectConfig jdbcConnectConfig = getJdbcConnectConfig(connectQuery, configurationApi);
            Result<ConnectionWrapper> createResult = createConnection(connectQuery.getServer(), jdbcConnectConfig);
            if (createResult.getSuccess()) {
                aliveConnection.put(idKey, createResult.getData());
            }
            return createResult;
        }
    }

    public static Result testConnect(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        HikariDataSource dataSource = null;
        try {
            ServerInfo server = connectQuery.getServer();
            JdbcConnectConfig jdbcConnectConfig = getJdbcConnectConfig(connectQuery, configurationApi);
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(jdbcConnectConfig.getDriver());
            // 4. 设置数据库连接 URL
            hikariConfig.setJdbcUrl(jdbcConnectConfig.getJdbcUrl());
            // 5. 设置数据库用户名和密码
            hikariConfig.setUsername(server.getUser());
            hikariConfig.setPassword(server.getPassword());
            // 7. 创建 HikariCP 数据源
            dataSource = new HikariDataSource(hikariConfig);
            dataSource.getConnection().close();
            return Result.success();
        } catch (Exception e) {
            if (e instanceof SQLException ) {
                SQLException se = (SQLException) e;
                return Result.error( se.getErrorCode(), se.getMessage());
            }
            return Result.error(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage());
        } finally {
            if (dataSource != null) {
                try {
                    dataSource.close();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }

    }

    public static JdbcConnectConfig getJdbcConnectConfig(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        // IConfigurationApi configurationApi = this.getServerConfigurationApi(connectQuery.getServer());
        ServerInfo server = connectQuery.getServer();
        String db = connectQuery.getDb();
        String schema = connectQuery.getSchema();
        JdbcConnectConfig jdbcConnectConfig = new JdbcConnectConfig();
        if (StringUtils.isNotEmpty(db)) {
            if (StringUtils.isNotEmpty(schema) && configurationApi.hasSchemaUrl()) {
                jdbcConnectConfig.setJdbcUrl(configurationApi.getSchemaUrl(server, db, schema));
            } else {
                jdbcConnectConfig.setJdbcUrl(configurationApi.getDbUrl(server, db));
            }
        } else {
            jdbcConnectConfig.setJdbcUrl(configurationApi.getServerUrl(server));
        }
        jdbcConnectConfig.setDriver(configurationApi.getDriver());
        jdbcConnectConfig.setDb(db);
        jdbcConnectConfig.setSchema(schema);
        jdbcConnectConfig.setMaximumPoolSize(configurationApi.getMaximumPoolSize());
        jdbcConnectConfig.setMinimumIdle(configurationApi.getMinimumIdle());
        return jdbcConnectConfig;
    }

    /**
     * 创建dataSource
     *
     * @return
     */
    public static Result<ConnectionWrapper> createConnection(ServerInfo server, JdbcConnectConfig connectConfig) {
        ConnectionWrapper connectionWrapper = new ConnectionWrapper();
        connectionWrapper.setDb(connectConfig.getDb());
        connectionWrapper.setSchema(connectConfig.getSchema());
        HikariDataSource dataSource = null;
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(connectConfig.getDriver());
            // 4. 设置数据库连接 URL
            hikariConfig.setJdbcUrl(connectConfig.getJdbcUrl());
            // 5. 设置数据库用户名和密码
            hikariConfig.setUsername(server.getUser());
            hikariConfig.setPassword(server.getPassword());
            // 6. 设置 HikariCP 连接池属性
            hikariConfig.setMaximumPoolSize(connectConfig.getMaximumPoolSize());
            hikariConfig.setMinimumIdle(connectConfig.getMinimumIdle());
            // 7. 创建 HikariCP 数据源
            dataSource = new HikariDataSource(hikariConfig);
            dataSource.getConnection().close();
            connectionWrapper.setDataSource(dataSource);
            return Result.success(connectionWrapper);
        } catch (Exception e) {
            if (e instanceof SQLException ) {
                SQLException se = (SQLException) e;
                return Result.error(se.getErrorCode(), se.getMessage());
            }
            return Result.error(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage());
        }
    }


}
