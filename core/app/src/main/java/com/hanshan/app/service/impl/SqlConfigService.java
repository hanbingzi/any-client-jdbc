package com.hanshan.app.service.impl;

import com.hanshan.AnsiSqlDialect;
import com.hanshan.BaseSqlDialect;
import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.model.ServerInfo;
import com.hanshan.app.service.ISqlConfigService;
import com.hanshan.common.types.JdbcServerTypeEnum;
import com.hanshan.dialect.MysqlDialect;
import com.hanshan.mysql8.config.Mysql8Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlConfigService implements ISqlConfigService {

    final static Logger logger = LoggerFactory.getLogger(SqlConfigService.class);

    private final static Mysql8Configuration mysql8Configuration = new Mysql8Configuration();
    private final static AnsiSqlDialect ansiSqlDialect = new AnsiSqlDialect();
    private final static MysqlDialect mysqlDialect = new MysqlDialect();

    public IJdbcConfigurationApi getServerConfigurationApi(ServerInfo server) {
        String serverType = server.getServerType();
        String version = server.getVersion();
        IJdbcConfigurationApi configurationApi = null;
        JdbcServerTypeEnum serverTypeEnum = JdbcServerTypeEnum.valueOf(serverType);
        //1.先判断server，然后判断version
        switch (serverTypeEnum) {
            case Mysql -> {
                configurationApi = mysql8Configuration;
            }
            case DB2 -> {
            }
            case RDJC -> {
            }
            case H2 -> {
            }
            case Hive -> {
            }
            case FileMaker -> {
            }
            case Teradata -> {
            }
            case SAP_HANA -> {
            }
            case Firebird -> {
            }
            case Spark_SQL -> {
            }
            case Redshift -> {
            }
            case Informix -> {
            }
            case ClickHouse -> {
            }
            case Impala -> {
            }
            case Flink -> {
            }
            case Presto -> {
            }
            case Vertica -> {
            }
            case Greenplum -> {
            }
            case Derby -> {
            }
            case Trino -> {
            }
            case DuckDB -> {
            }
            default -> {
                logger.error("server not find");
            }
        }
        return configurationApi;

    }

    public BaseSqlDialect getSqlDialect(ServerInfo server) {
        String serverType = server.getServerType();
        JdbcServerTypeEnum serverTypeEnum = JdbcServerTypeEnum.valueOf(serverType);
        //1.先判断server，然后判断version
        switch (serverTypeEnum) {
            case Mysql -> {
                return mysqlDialect;
            }
            case DB2 -> {
            }
            case RDJC -> {
            }
            case H2 -> {
            }
            case Hive -> {
            }
            case FileMaker -> {
            }
            case Teradata -> {
            }
            case SAP_HANA -> {
            }
            case Firebird -> {
            }
            case Spark_SQL -> {
            }
            case Redshift -> {
            }
            case Informix -> {
            }
            case ClickHouse -> {
            }
            case Impala -> {
            }
            case Flink -> {
            }
            case Presto -> {
            }
            case Vertica -> {
            }
            case Greenplum -> {
            }
            case Derby -> {
            }
            case Trino -> {
            }
            case DuckDB -> {
            }
            default -> {
                logger.error("server not find");
            }
        }
        return ansiSqlDialect;

    }


}
