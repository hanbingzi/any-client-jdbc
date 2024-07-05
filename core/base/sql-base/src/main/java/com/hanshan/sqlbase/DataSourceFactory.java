package com.hanshan.sqlbase;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ServerInfo;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.common.types.JdbcServerTypeEnum;
import com.hanshan.common.types.ResponseEnum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSourceFactory {
    public final static Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
    private static final Map<String, ConnectionWrapper> aliveConnection = new HashMap<>();

    public static void removeConnection(String idKey) {
//        for (String key : aliveConnection.keySet()) {
//            if (key.equals(idKey) || key.startsWith(idKey)) {
//                logger.info("关闭连接："+key);
//                end(key);
//            }
//        }
        Iterator<Map.Entry<String, ConnectionWrapper>> iterator = aliveConnection.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ConnectionWrapper> entry = iterator.next();
            String key = entry.getKey();
            if (key.equals(idKey) || key.startsWith(idKey)) {
                end(key);
                iterator.remove();
            }
        }

    }

    public static void removeAllConnection() {

        Iterator<Map.Entry<String, ConnectionWrapper>> iterator = aliveConnection.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ConnectionWrapper> entry = iterator.next();
            String key = entry.getKey();
            end(key);
            iterator.remove();
        }

    }

    public static void end(String idKey) {
        try {
            ConnectionWrapper connectionWrapper = aliveConnection.get(idKey);
            if (connectionWrapper != null) {
                HikariDataSource dataSource = connectionWrapper.getDataSource();
                dataSource.close();
                logger.info("success close " + idKey);
            }
        } catch (Exception e) {
            logger.error("-------------" + e.getMessage());
            e.printStackTrace();
            //throw e;
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
            JdbcConnectConfig connectConfig = getJdbcConnectConfig(connectQuery, configurationApi);
            HikariConfig hikariConfig = createHikariConfig(server, connectConfig);
            // 7. 创建 HikariCP 数据源
            dataSource = new HikariDataSource(hikariConfig);
            dataSource.getConnection().close();
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SQLException) {
                SQLException se = (SQLException) e;
                return Result.error(se.getErrorCode(), se.getMessage());
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

    //oceanbase 连接说明：https://www.oceanbase.com/docs/common-oceanbase-database-cn-1000000000640068
    public static String getUsername(ServerInfo server) {
        String serverType = server.getServerType();
        JdbcServerTypeEnum serverTypeEnum = JdbcServerTypeEnum.valueOf(serverType);
        if (JdbcServerTypeEnum.OceanBase == serverTypeEnum) {
            if (StringUtils.isNotEmpty(server.getTenant())) {
                System.out.println("oceanbase---->tenant" + server.getTenant());
                return server.getUser() + "@" + server.getTenant();
            }
        }
        return server.getUser();
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
        if (StringUtils.isNotEmpty(configurationApi.getDriver())) {
            jdbcConnectConfig.setDriver(configurationApi.getDriver());
        }
        jdbcConnectConfig.setDb(db);
        jdbcConnectConfig.setSchema(schema);
        if (configurationApi.getMaximumPoolSize() != null && configurationApi.getMaximumPoolSize() != 0)
            jdbcConnectConfig.setMaximumPoolSize(configurationApi.getMaximumPoolSize());
        if (configurationApi.getMinimumIdle() != null && configurationApi.getMinimumIdle() != 0)
            jdbcConnectConfig.setMinimumIdle(configurationApi.getMinimumIdle());
        //时间单位，前端输入的为秒，实际使用为毫秒
        if (configurationApi.getMaxLifeTime() != null && configurationApi.getMaxLifeTime() != 0)
            jdbcConnectConfig.setMaxLifeTime(configurationApi.getMaxLifeTime() );
        if (configurationApi.getIdleTimeout() != null && configurationApi.getIdleTimeout() != 0)
            jdbcConnectConfig.setIdleTimeout(configurationApi.getIdleTimeout() );
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
        logger.info("创建链接--》" + connectConfig.getJdbcUrl());
        try {

            HikariConfig hikariConfig = createHikariConfig(server, connectConfig);
            // 7. 创建 HikariCP 数据源
            dataSource = new HikariDataSource(hikariConfig);
            dataSource.getConnection().close();
            connectionWrapper.setDataSource(dataSource);
            return Result.success(connectionWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SQLException) {
                SQLException se = (SQLException) e;
                return Result.error(se.getErrorCode(), se.getMessage());
            }
            return Result.error(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage());
        }
    }

    public static HikariConfig createHikariConfig(ServerInfo server, JdbcConnectConfig connectConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        if (StringUtils.isNotEmpty(connectConfig.getDriver())) {
            hikariConfig.setDriverClassName(connectConfig.getDriver());
        }
        // 4. 设置数据库连接 URL
        hikariConfig.setJdbcUrl(connectConfig.getJdbcUrl());
        String username = getUsername(server);
        // 5. 设置数据库用户名和密码
        if (StringUtils.isNotEmpty(username))
            hikariConfig.setUsername(username);
        if (StringUtils.isNotEmpty(server.getPassword()))
            hikariConfig.setPassword(server.getPassword());
        // 6. 设置 HikariCP 连接池属性
        hikariConfig.setMaximumPoolSize(connectConfig.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(connectConfig.getMinimumIdle());
        hikariConfig.setMaxLifetime(connectConfig.getMaxLifeTime());
        hikariConfig.setIdleTimeout(connectConfig.getIdleTimeout());

        return hikariConfig;
    }


}
