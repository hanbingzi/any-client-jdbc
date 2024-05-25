package com.hanshan.db211.config;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ServerInfo;
import lombok.Data;

@Data
public class DB211Configuration implements IJdbcConfiguration {
    private String driver = "com.ibm.db2.jcc.DB2Driver";
    //private String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=test"
    private Boolean hasDatabaseUrl = true;
    private Boolean hasSchemaUrl = false;
    private Boolean hasDatabase = true;
    private Boolean hasSchema = true;

    private Integer maximumPoolSize = 3;
    private Integer minimumIdle = 1;
    private Long maxLifeTime = 10 * 60 * 1000L;

    @Override
    public String getDriver() {
        return this.driver;
    }

    @Override
    public String getServerUrl(ServerInfo server) {
        String jdbcUrl = "jdbc:db2://%s:%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort());
    }

    @Override
    public String getDbUrl(ServerInfo server, String db) {
        String jdbcUrl = "jdbc:db2://%s:%s/%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db);
    }

    @Override
    public String getSchemaUrl(ServerInfo server, String db, String schema) {
        //经过测试，这种路径写法无法使用
        String jdbcUrl = "jdbc:db2://%s:%s/%s:currentSchema=%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db, schema);
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
