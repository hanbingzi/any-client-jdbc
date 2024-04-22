package com.hanshan.app.config;

import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "anyclient.jdbc.mysql8")
@Data
public class Mysql8Configuration implements IConfigurationApi{
    private String driver;
    private String jdbcUrl;
    private Boolean hasDatabase=false;
    private Boolean hasSchema=false;
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
}
