package com.hanshan.mysql8.config;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.model.ServerInfo;
import lombok.Data;

@Data
public class Mysql8Configuration implements IJdbcConfigurationApi {
    private String driver = "com.mysql.cj.jdbc.Driver";
    //private String jdbcUrl = "jdbc:mysql://localhost:3306/database";
    //private String jdbcUrl = "jdbc:mysql://";
    private Boolean hasDatabase = true;
    private Boolean hasSchema = false;
    private Integer maximumPoolSize = 3;
    private Integer minimumIdle = 1;

    @Override
    public String getDriver() {
        return this.driver;
    }

    @Override
    public String getServerUrl(ServerInfo server) {
        String jdbcUrl = "jdbc:mysql://%s:%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort());
    }

    @Override
    public String getDbUrl(ServerInfo server, String db) {
        String jdbcUrl = "jdbc:mysql://%s:%s/%s";
        return String.format(jdbcUrl, server.getHost(), server.getPort(), db);
    }

    @Override
    public String getSchemaUrl(ServerInfo server, String db, String schema) {
        return null;
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
}
