package com.hanshan.common.config;

import com.hanshan.common.pojo.model.ServerInfo;
import lombok.Data;

@Data
public class JdbcConfigurationDefault {
    protected Integer maximumPoolSize = 3;
    protected Integer minimumIdle = 1;
    protected Long maxLifeTime = 10 * 60 * 1000L;
    protected Long idleTimeout = 10 * 60 * 1000L;

    public String getSchemaUrl(ServerInfo server, String db, String schema) {
        return null;
    }

    public String getDefaultSchema() {
        return null;
    }


}
