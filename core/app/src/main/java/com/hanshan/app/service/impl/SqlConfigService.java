package com.hanshan.app.service.impl;

import com.hanshan.app.service.ISqlConfigService;
import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.dialect.AnsiSqlDialect;
import com.hanshan.common.dialect.BaseSqlDialect;
import com.hanshan.common.pojo.model.ServerInfo;
import com.hanshan.common.types.JdbcServerTypeEnum;
import com.hanshan.db211.config.ClickHouse05Configuration;
import com.hanshan.db211.config.DB211Configuration;
import com.hanshan.dialect.MysqlDialect;
import com.hanshan.mssql12.config.Mssql12Configuration;
import com.hanshan.postgresql42.config.Mysql8Configuration;
import com.hanshan.postgresql42.config.Postgresql42Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlConfigService implements ISqlConfigService {

    final static Logger logger = LoggerFactory.getLogger(SqlConfigService.class);

    private final static Mysql8Configuration mysql8Configuration = new Mysql8Configuration();
    private final static Mssql12Configuration mssql12Configuration = new Mssql12Configuration();
    private final static Postgresql42Configuration postgresql42Configuration = new Postgresql42Configuration();
    private final static DB211Configuration db211Configuration = new DB211Configuration();
    private final static ClickHouse05Configuration clickHouse05Configuration = new ClickHouse05Configuration();
    private final static AnsiSqlDialect ansiSqlDialect = new AnsiSqlDialect();
    private final static MysqlDialect mysqlDialect = new MysqlDialect();

    public IJdbcConfiguration getServerConfigurationApi(ServerInfo server) {
        String serverType = server.getServerType();
        String version = server.getVersion();
        JdbcServerTypeEnum serverTypeEnum = JdbcServerTypeEnum.valueOf(serverType);
        //1.先判断server，然后判断version
        switch (serverTypeEnum) {
            case Mysql:
                return mysql8Configuration;
            case Postgresql:
                return postgresql42Configuration;
            case SQLServer:
                return mssql12Configuration;
            case DB2:
                return db211Configuration;
            case RDJC:
                break;
            case H2:
                break;
            case Hive:
                break;
            case FileMaker:
                break;
            case Teradata:
                break;
            case SAP_HANA:
                break;
            case Firebird:
                break;
            case Spark_SQL:
                break;
            case Redshift:
                break;
            case Informix:
                break;
            case ClickHouse:
                return clickHouse05Configuration;
            case Impala:
                break;
            case Flink:
                break;
            case Presto:
                break;
            case Vertica:
                break;
            case Greenplum:
                break;
            case Derby:
                break;
            case Trino:
                break;
            case DuckDB:
                break;
            default:
                logger.error("server not find");
        }
        return null;
    }

    public BaseSqlDialect getSqlDialect(ServerInfo server) {
        String serverType = server.getServerType();
        JdbcServerTypeEnum serverTypeEnum = JdbcServerTypeEnum.valueOf(serverType);
        //1.先判断server，然后判断version
        switch (serverTypeEnum) {
            case Mysql:
                return mysqlDialect;
            case DB2:
                break;
            case RDJC:
                break;
            case H2:
                break;
            case Hive:
                break;
            case FileMaker:
                break;
            case Teradata:
                break;
            case SAP_HANA:
                break;
            case Firebird:
                break;
            case Spark_SQL:
                break;
            case Redshift:
                break;
            case Informix:
                break;
            case ClickHouse:
                break;
            case Impala:
                break;
            case Flink:
                break;
            case Presto:
                break;
            case Vertica:
                break;
            case Greenplum:
                break;
            case Derby:
                break;
            case Trino:
                break;
            case DuckDB:
                break;
            default:
                logger.error("server not find");

        }
        return ansiSqlDialect;

    }


}
