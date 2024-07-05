package com.hanshan.presto02.config;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ServerInfo;
import lombok.Data;

@Data
public class Presto02Configuration implements IJdbcConfiguration {
    private String driver = "com.facebook.presto.jdbc.PrestoDriver";
    //private String jdbcUrl = jdbc:trino://localhost:8081/test
    private Boolean hasDatabaseUrl = true;
    private Boolean hasSchemaUrl = true;
    private Boolean hasDatabase = true;
    private Boolean hasSchema = true;

    private Integer maximumPoolSize = 3;
    private Integer minimumIdle = 1;
    private Long maxLifeTime = 10 * 60 * 1000L;
    private Long idleTimeout = 10 * 60 * 1000L;

    private Presto02Configuration(Integer maximumPoolSize, Integer minimumIdle, Long maxLifeTime, Long idleTimeout) {
        this.maximumPoolSize = maximumPoolSize;
        this.minimumIdle = minimumIdle;
        this.maxLifeTime = maxLifeTime;
        this.idleTimeout = idleTimeout;
    }


    public static Presto02Configuration getInstance(Integer maximumPoolSize, Integer minimumIdle, Long maxLifeTime, Long idleTimeout) {
        return new Presto02Configuration(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
    }
    @Override
    public String getDriver() {
        return this.driver;
    }
    //jdbc:clickhouse://localhost:8123/default
    //jdbc:clickhouse://server1:8123,server2:8123,server3:8123/database
    @Override
    public String getServerUrl(ServerInfo server) {
        String jdbcUrl = "jdbc:presto://%s:%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort());
    }

    @Override
    public String getDbUrl(ServerInfo server, String db) {
        String jdbcUrl = "jdbc:presto://%s:%s/%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db);
    }

    @Override
    public String getSchemaUrl(ServerInfo server, String db, String schema) {
        String jdbcUrl = "jdbc:presto://%s:%s/%s/%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db,schema);
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

    @Override
    public Integer getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    @Override
    public Integer getMinimumIdle() {
        return this.minimumIdle;
    }

    @Override
    public Long getMaxLifeTime() {
        return this.maxLifeTime;
    }


    @Override
    public String getDefaultSchema() {
        return "dbo";
    }
}
