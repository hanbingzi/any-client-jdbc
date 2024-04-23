package com.hanshan.sqlbase;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSourceManager {
    public final static Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
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

    public static Result<Object> getConnection(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi) {
        String idKey = ConnectIdKey.getConnectKey(connectQuery);
        //ConnectionWrapper connectionWrapper = null;
        if (aliveConnection.containsKey(idKey)) {
            ConnectionWrapper connectionWrapper = aliveConnection.get(idKey);
            return Result.success(connectionWrapper);
        } else {
            JdbcConnectConfig jdbcConnectConfig = getJdbcConnectConfig(connectQuery, configurationApi);
            Result<Object> createResult = createConnection(jdbcConnectConfig);
            if (createResult.getSuccess()) {
                aliveConnection.put(idKey, (ConnectionWrapper) createResult.getData());
            }
            return createResult;
        }
    }

    public static Result<Object> testConnect(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi) {
        HikariDataSource dataSource = null;
        try {
            JdbcConnectConfig jdbcConnectConfig = getJdbcConnectConfig(connectQuery, configurationApi);
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(jdbcConnectConfig.getDriver());
            // 4. 设置数据库连接 URL
            hikariConfig.setJdbcUrl(jdbcConnectConfig.getJdbcUrl());
            // 5. 设置数据库用户名和密码
            hikariConfig.setUsername(jdbcConnectConfig.getUsername());
            hikariConfig.setPassword(jdbcConnectConfig.getPassword());
            // 7. 创建 HikariCP 数据源
            dataSource = new HikariDataSource(hikariConfig);
            dataSource.getConnection().close();
            return Result.success();
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
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

    public static JdbcConnectConfig getJdbcConnectConfig(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi) {
        // IConfigurationApi configurationApi = this.getServerConfigurationApi(connectQuery.getServer());
        String db = connectQuery.getDb();
        String schema = connectQuery.getSchema();
        JdbcConnectConfig jdbcConnectConfig = new JdbcConnectConfig();
        if (StringUtils.isNotEmpty(db)) {
            if (StringUtils.isNotEmpty(schema)) {
                jdbcConnectConfig.setJdbcUrl(configurationApi.getSchemaUrl());
            } else {
                jdbcConnectConfig.setJdbcUrl(configurationApi.getDbUrl());
            }
        } else {
            jdbcConnectConfig.setJdbcUrl(configurationApi.getServerUrl());
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
    public static Result<Object> createConnection(JdbcConnectConfig connectConfig) {
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
            hikariConfig.setUsername(connectConfig.getUsername());
            hikariConfig.setPassword(connectConfig.getPassword());
            // 6. 设置 HikariCP 连接池属性
            hikariConfig.setMaximumPoolSize(connectConfig.getMaximumPoolSize());
            hikariConfig.setMinimumIdle(connectConfig.getMinimumIdle());
            // 7. 创建 HikariCP 数据源
            dataSource = new HikariDataSource(hikariConfig);
            dataSource.getConnection().close();
            connectionWrapper.setDataSource(dataSource);
            return Result.success(connectionWrapper);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return Result.error(e.getErrorCode(), e.getMessage());
        }
    }


}
