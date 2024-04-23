package com.hanshan;

public interface IJdbcConfigurationApi {

    String getDriver();

    String getServerUrl();

    String getDbUrl();

    String getSchemaUrl();

    Boolean hasDatabase();

    Boolean hasSchema();

    Integer getMaximumPoolSize();

    Integer getMinimumIdle();


}
