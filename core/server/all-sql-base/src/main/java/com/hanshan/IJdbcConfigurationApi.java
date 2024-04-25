package com.hanshan;

import com.hanshan.api.model.ServerInfo;

public interface IJdbcConfigurationApi {

    String getDriver();

    String getServerUrl(ServerInfo server);

    String getDbUrl(ServerInfo server, String db);

    String getSchemaUrl(ServerInfo server, String db, String schema);

    Boolean hasDatabase();

    Boolean hasSchema();

    Integer getMaximumPoolSize();

    Integer getMinimumIdle();


}
