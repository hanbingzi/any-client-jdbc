package com.hanshan.mysql8.config;

import com.hanshan.IJdbcConfigurationApi;
import lombok.Data;

@Data
public class Mysql8Configuration implements IJdbcConfigurationApi {
    private String driver = "";
    private String jdbcUrl = "";
    private Boolean hasDatabase = true;
    private Boolean hasSchema = false;
    private Integer maximumPoolSize = 3;
    private Integer minimumIdle = 1;

    @Override
    public String getDriver() {
        return this.driver;
    }

    @Override
    public String getServerUrl() {
        return this.jdbcUrl;
    }

    @Override
    public String getDbUrl() {
        return null;
    }

    @Override
    public String getSchemaUrl() {
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
