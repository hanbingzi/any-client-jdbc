package com.hanshan.tdengine3.config;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.config.JdbcConfigurationDefault;
import com.hanshan.common.pojo.model.ServerInfo;
import lombok.Data;


public class TDEngine3Configuration extends JdbcConfigurationDefault implements IJdbcConfiguration {
    /**
     * 原生
     * Class.forName("com.taosdata.jdbc.TSDBDriver");
     * String jdbcUrl = "jdbc:TAOS://taosdemo.com:6030/power?user=root&password=taosdata";
     * restful
     * Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
     * String jdbcUrl = "jdbc:TAOS-RS://taosdemo.com:6041/power?user=root&password=taosdata";
     */
    private final String driver = "com.taosdata.jdbc.rs.RestfulDriver";
    private final Boolean hasDatabaseUrl = true;
    private final Boolean hasSchemaUrl = false;
    private final Boolean hasDatabase = true;
    private final Boolean hasSchema = false;


    private TDEngine3Configuration(Integer maximumPoolSize, Integer minimumIdle, Long maxLifeTime, Long idleTimeout) {
        this.maximumPoolSize = maximumPoolSize;
        this.minimumIdle = minimumIdle;
        if (maxLifeTime != null)
            this.maxLifeTime = maxLifeTime;
        if (idleTimeout != null)
            this.idleTimeout = idleTimeout;
    }

    public static TDEngine3Configuration getInstance(Integer maximumPoolSize, Integer minimumIdle, Long maxLifeTime, Long idleTimeout) {
        return new TDEngine3Configuration(maximumPoolSize, minimumIdle, maxLifeTime, idleTimeout);
    }

    @Override
    public String getDriver() {
        return this.driver;
    }

    @Override
    public String getServerUrl(ServerInfo server) {
        String jdbcUrl = "jdbc:TAOS-RS://%s:%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort());
    }

    @Override
    public String getDbUrl(ServerInfo server, String db) {
        String jdbcUrl = "jdbc:TAOS-RS://%s:%s/%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db);
    }

    @Override
    public Boolean hasDatabaseUrl() {
        return this.hasDatabaseUrl;
    }

    @Override
    public Boolean hasSchemaUrl() {
        return this.hasSchemaUrl;
    }

    @Override
    public Boolean hasDatabase() {
        return this.hasDatabase;
    }

    @Override
    public Boolean hasSchema() {
        return this.hasSchema;
    }


}
