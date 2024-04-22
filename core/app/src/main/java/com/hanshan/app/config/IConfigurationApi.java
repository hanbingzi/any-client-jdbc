package com.hanshan.app.config;

public interface IConfigurationApi {

    String getDriver();

    String getServerUrl();

    String getDbUrl();

    String getSchemaUrl();

    Boolean hasDatabase();

    Boolean hasSchema();


}
