package com.hanshan.app.service.impl;

import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.app.service.ISqlConfigService;
import com.hanshan.clickhouse05.config.ClickHouse05Configuration;
import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.dialect.AnsiSqlDialect;
import com.hanshan.common.dialect.BaseSqlDialect;
import com.hanshan.common.pojo.model.ServerInfo;
import com.hanshan.common.types.JdbcServerTypeEnum;
import com.hanshan.db211.config.DB211Configuration;
import com.hanshan.dialect.MysqlDialect;
//import com.hanshan.hive3.config.Hive3Configuration;
import com.hanshan.mssql12.config.Mssql12Configuration;
import com.hanshan.oceanbase2.config.Oceanbase2Configuration;
import com.hanshan.postgresql42.config.Mysql8Configuration;
import com.hanshan.postgresql42.config.Postgresql42Configuration;
import com.hanshan.presto02.config.Presto02Configuration;
import com.hanshan.trino4.config.Trino4Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlConfigService implements ISqlConfigService {

    final static Logger logger = LoggerFactory.getLogger(SqlConfigService.class);

    private final static AnsiSqlDialect ansiSqlDialect = new AnsiSqlDialect();
    private final static MysqlDialect mysqlDialect = new MysqlDialect();

    public IJdbcConfiguration getServerConfigurationApi(ServerInfo server) throws Exception {
        String serverType = server.getServerType();
        String version = server.getVersion();
        //连接字符编码
         String connectEncoding = server.getConnectEncoding();
        //连接池最大连接数
        Integer maximumPoolSize = server.getMaximumPoolSize();
        //连接池最小连接数
        Integer minimumIdle = server.getMinimumIdle();
        //连接会话时长
        Long maxLifeTime = server.getMaxLifeTime();
        //空闲连接超时时间
        Long idleTimeout= server.getIdleTimeout();
        JdbcServerTypeEnum serverTypeEnum = JdbcServerTypeEnum.valueOf(serverType);
        //1.先判断server，然后判断version
        switch (serverTypeEnum) {
            case TiDB:
            case Mysql:
                return Mysql8Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case Postgresql:
                return Postgresql42Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case SQLServer:
                return Mssql12Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case DB2:
                return DB211Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case OceanBase:
                return Oceanbase2Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case RDJC:
                break;
            case H2:
                break;
            case Hive:
//               return hive3Configuration;
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
                return ClickHouse05Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case Impala:
                break;
            case Flink:
                break;
            case Vertica:
                break;
            case Greenplum:
                break;
            case Derby:
                break;
            case Presto:
                return Presto02Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case Trino:
                return Trino4Configuration.getInstance(maximumPoolSize,minimumIdle,maxLifeTime,idleTimeout);
            case DuckDB:
                break;
            default:
                logger.error("server not find");
        }
        throw new ParamErrorException();

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
