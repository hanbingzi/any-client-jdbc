package com.hanshan.sqlbase;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.PrimaryInfo;
import com.hanshan.common.pojo.model.VFTSPInfo;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.sqlbase.types.SqlTableEnum;
import com.hanshan.sqlbase.utils.SqlValueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlMetaOperator {
    public final static Logger logger = LoggerFactory.getLogger(SqlMetaOperator.class);


    public static Result testConnect(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        return DataSourceFactory.testConnect(connectQuery, configurationApi);
    }

    /**
     * 展示所有的库
     *
     * @param connectQuery
     * @param configurationApi
     * @return
     */
    public static Result<List<String>> showDatabase(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {

        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getCatalogs();
            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
            return Result.success(databases);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    //展示所有的schema
    public static Result<List<String>> showSchema(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getSchemas(connectQuery.getDb(), null);
            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
            return Result.success(databases);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    /**
     * //展示所有的table
     * //展示所有的view
     *
     * @param connectQuery
     * @param configurationApi
     * @param tableEnum
     * @return
     */
    public static Result<List<VFTSPInfo>> showTables(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, SqlTableEnum tableEnum) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String schema = configurationApi.hasSchema() ? connectQuery.getSchema() : null;
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getTables(connectQuery.getDb(), schema, null, new String[]{tableEnum.name()});
            List<VFTSPInfo> tables = new ArrayList<>();
            //logger.info("---showtables ==={}",resultSet.getMetaData());
            while (resultSet.next()) {
                VFTSPInfo nameComment = new VFTSPInfo();
                nameComment.setName(resultSet.getString("TABLE_NAME"));
                nameComment.setComment(resultSet.getString("REMARKS"));
                tables.add(nameComment);
            }
            return Result.success(tables);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    /**
     * 展示所有的function
     */

    public static Result<List<VFTSPInfo>> showFunctions(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String schema = configurationApi.hasSchema() ? connectQuery.getSchema() : null;
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getFunctions(connectQuery.getDb(), schema, null);
            List<VFTSPInfo> tables = new ArrayList<>();
            //logger.info("---showtables ==={}",resultSet.getMetaData());
            while (resultSet.next()) {
                VFTSPInfo nameComment = new VFTSPInfo();
                nameComment.setName(SqlValueUtils.resolveVFTSPName(resultSet.getString("FUNCTION_NAME")));
                nameComment.setComment(resultSet.getString("REMARKS"));
                tables.add(nameComment);
            }
            return Result.success(tables);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    //展示所有的trigger

    //展示所有的sequence

    public static Result<List<VFTSPInfo>> showProcedures(ConnectQuery connectQuery, IJdbcConfiguration configurationApi) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String schema = configurationApi.hasSchema() ? connectQuery.getSchema() : null;
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getProcedures(connectQuery.getDb(), schema, null);
            List<VFTSPInfo> tables = new ArrayList<>();
            //logger.info("---showtables ==={}",resultSet.getMetaData());
            while (resultSet.next()) {
                VFTSPInfo nameComment = new VFTSPInfo();
                String procedureType = resultSet.getString("PROCEDURE_TYPE");
                String procedureName = resultSet.getString("PROCEDURE_NAME");
                String remarks = resultSet.getString("REMARKS");
                nameComment.setName(SqlValueUtils.resolveVFTSPName(procedureName));
                nameComment.setComment(remarks);
                tables.add(nameComment);
            }
            return Result.success(tables);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    public static Result<List<PrimaryInfo>> showPrimary(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, String table) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String schema = configurationApi.hasSchema() ? connectQuery.getSchema() : null;
            connection = connectResult.getData();
            resultSet = connection.getMetaData().getPrimaryKeys(connectQuery.getDb(), schema, table);
            List<PrimaryInfo> primaryInfos = new ArrayList<>();
            //logger.info("---showtables ==={}",resultSet.getMetaData());
            while (resultSet.next()) {
                PrimaryInfo primaryInfo = new PrimaryInfo();
                String tableName = resultSet.getString("TABLE_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                Integer seq = resultSet.getInt("KEY_SEQ");
                String pkName = resultSet.getString("PK_NAME");
                primaryInfo.setTableName(tableName);
                primaryInfo.setColumnName(columnName);
                primaryInfo.setOrdinal(seq);
                primaryInfo.setConstraint(pkName);
                primaryInfos.add(primaryInfo);
            }
            return Result.success(primaryInfos);
        } catch (SQLException e) {
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("SqlConnRunner close error in showDatabase,error code{},error message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    //纯sql执行
    //执行插入和更改，无参数
    //执行插入和更改，有参数
    //执行查询，无参数
    //执行查询，有参数

}
