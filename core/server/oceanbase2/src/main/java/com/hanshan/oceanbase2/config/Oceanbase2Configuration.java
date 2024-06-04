package com.hanshan.oceanbase2.config;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ServerInfo;
import lombok.Data;

@Data
public class Oceanbase2Configuration implements IJdbcConfiguration {
    private String driver = "com.alipay.oceanbase.jdbc.Driver";
    //String url = "jdbc:oceanbase://your-oceanbase-server-ip:port/your-database-name";
    private Boolean hasDatabaseUrl = true;
    private Boolean hasSchemaUrl = false;
    private Boolean hasDatabase = true;
    private Boolean hasSchema = false;

    private Integer maximumPoolSize = 3;
    private Integer minimumIdle = 1;
    private Long maxLifeTime = 10 * 60 * 1000L;

    @Override
    public String getDriver() {
        return this.driver;
    }
    //jdbc:clickhouse://localhost:8123/default
    //jdbc:clickhouse://server1:8123,server2:8123,server3:8123/database
    @Override
    public String getServerUrl(ServerInfo server) {
        String jdbcUrl = "jdbc:oceanbase://%s:%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort());
    }

    @Override
    public String getDbUrl(ServerInfo server, String db) {
        String jdbcUrl = "jdbc:oceanbase://%s:%s/%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db);
    }

    @Override
    public String getSchemaUrl(ServerInfo server, String db, String schema) {
        return null;
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
