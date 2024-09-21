package com.hanshan.common.config;

import com.hanshan.common.pojo.model.ServerInfo;

public interface IJdbcConfiguration {


    String getDriver();

    String getServerUrl(ServerInfo server);

    String getDbUrl(ServerInfo server, String db);

    String getSchemaUrl(ServerInfo server, String db, String schema);

    /**
     * 是否配置DB url
     *
     * @return
     */
    Boolean hasDatabaseUrl();

    /**
     * 是否可配置SchemaUrl
     *
     * @return
     */
    Boolean hasSchemaUrl();

    /**
     * 是否有数据库层级
     *
     * @return
     */
    Boolean hasDatabase();

    /**
     * 是否有模式层级
     *
     * @return
     */
    Boolean hasSchema();

    Integer getMaximumPoolSize();

    Integer getMinimumIdle();

    Long getMaxLifeTime();

    Long getIdleTimeout();

    String getDefaultSchema();

    // IJdbcConfiguration getInstance(Integer maximumPoolSize, Integer minimumIdle, Long maxLifeTime, Long idleTimeout);


}
