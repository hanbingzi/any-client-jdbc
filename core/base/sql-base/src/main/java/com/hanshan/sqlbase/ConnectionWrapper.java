package com.hanshan.sqlbase;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionWrapper {
    /**
     * 数据源
     */
    private HikariDataSource dataSource;
    /**
     * 连接的数据库
     */
    private String db;
    /**
     *
     */
    private String schema;

}
