package com.hanshan.sqlbase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JdbcConnectConfig {
    private String jdbcUrl;
    private String driver;
    private Integer maximumPoolSize = 5;
    private Integer minimumIdle = 1;
    private String db;
    private String schema;
}
