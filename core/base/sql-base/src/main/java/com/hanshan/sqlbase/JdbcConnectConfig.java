package com.hanshan.sqlbase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdbcConnectConfig {
    private String jdbcUrl;
    private String driver;
    private Integer maximumPoolSize = 5;
    private Integer minimumIdle = 1;
    private Long maxLifeTime = 10 * 60 * 1000L;
    private String db;
    private String schema;
}
