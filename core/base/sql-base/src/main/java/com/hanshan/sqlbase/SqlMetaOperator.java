package com.hanshan.sqlbase;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ColumnMeta;
import com.hanshan.common.pojo.model.PrimaryInfo;
import com.hanshan.common.pojo.model.VFTSPInfo;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.common.types.JDBCJavaTypes;
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

    /**
     * TABLE_CAT String => table catalog (may be null)
     * TABLE_SCHEM String => table schema (may be null)
     * TABLE_NAME String => table name
     * COLUMN_NAME String => column name
     * DATA_TYPE int => SQL type from java.sql.Types
     * TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
     * COLUMN_SIZE int => column size.
     * BUFFER_LENGTH is not used.
     * DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
     * NUM_PREC_RADIX int => Radix (typically either 10 or 2)
     * NULLABLE int => is NULL allowed.
     * columnNoNulls - might not allow NULL values
     * columnNullable - definitely allows NULL values
     * columnNullableUnknown - nullability unknown
     * REMARKS String => comment describing column (may be null)
     * COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
     * SQL_DATA_TYPE int => unused
     * SQL_DATETIME_SUB int => unused
     * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
     * ORDINAL_POSITION int => index of column in table (starting at 1)
     * IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
     * YES --- if the column can include NULLs
     * NO --- if the column cannot include NULLs
     * empty string --- if the nullability for the column is unknown
     * SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
     * SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     * SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     * SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
     * IS_AUTOINCREMENT String => Indicates whether this column is
     * YES --- if the column is auto incremented
     * NO --- if the column is not auto incremented
     * empty string --- if it cannot be determined whether the column is auto incremented
     * IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
     * YES --- if this a generated column
     * NO --- if this not a generated column
     * empty string --- if it cannot be determined whether this is a generated column
     *
     * @param connectQuery
     * @param configurationApi
     * @param table
     * @return
     */
    public static Result<List<ColumnMeta>> showColumn(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, String table) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.error(connectResult);
        }
        Connection connection = null;
        ResultSet primaryResultSet = null;
        ResultSet columnResultSet = null;
        List<String> primaryList = new ArrayList<>();
        try {
            String schema = configurationApi.hasSchema() ? connectQuery.getSchema() : null;
            connection = connectResult.getData();
            primaryResultSet = connection.getMetaData().getPrimaryKeys(connectQuery.getDb(), schema, table);
            while (primaryResultSet.next()) {
                String columnName = primaryResultSet.getString("COLUMN_NAME");
                primaryList.add(columnName);
            }
            columnResultSet = connection.getMetaData().getColumns(connectQuery.getDb(), schema, table, null);
            List<ColumnMeta> columnMetaList = new ArrayList<>();
            //logger.info("---showtables ==={}",resultSet.getMetaData());
            while (columnResultSet.next()) {
                ColumnMeta columnMeta = new ColumnMeta();
                //COLUMN_NAME String => column name
                String columnName = columnResultSet.getString("COLUMN_NAME");
                //DATA_TYPE int => SQL type from java.sql.Types
                Integer columnTypeId = columnResultSet.getInt("DATA_TYPE");
                //TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
                String columnType = columnResultSet.getString("TYPE_NAME");
                //COLUMN_SIZE int => column size.
                Integer columnSize = columnResultSet.getInt("COLUMN_SIZE");
                //DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
                Integer digits = columnResultSet.getInt("DECIMAL_DIGITS");
                //NULLABLE int => is NULL allowed.
                Integer nullable = columnResultSet.getInt("NULLABLE");
                //REMARKS String => comment describing column (may be null)
                String remarks = columnResultSet.getString("REMARKS");
                //COLUMN_DEF String => default value for the column, which should be interpreted as
                String columnDef = columnResultSet.getString("COLUMN_DEF");
                //IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
                String isAutoincrement = columnResultSet.getString("IS_AUTOINCREMENT");
                columnMeta.setName(columnName);
                columnMeta.setLabel(columnName);
                columnMeta.setColumnTypeId(columnTypeId);
                columnMeta.setColumnType(columnType);
                columnMeta.setLength(columnSize);
                columnMeta.setScale(digits);
                columnMeta.setNullable(String.valueOf(nullable));
                columnMeta.setComment(remarks);
                columnMeta.setColumnDefinition(columnDef);
                columnMeta.setTableName(table);

                columnMeta.setIsAutoIncrement("YES".equals(isAutoincrement));

                JDBCJavaTypes jdbcColumnType = JDBCJavaTypes.getType(columnTypeId, columnType);
                columnMeta.setDataType(jdbcColumnType.getName());
                columnMeta.setJdbcDataType(jdbcColumnType);

                if(primaryList.contains(columnName)){
                    columnMeta.setIsPrimary(true);
                    columnMeta.setPk("P");
                }
                columnMetaList.add(columnMeta);
            }
            return Result.success(columnMetaList);
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.error(e.getErrorCode(), e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (primaryResultSet != null) {
                    primaryResultSet.close();
                }
                if (columnResultSet != null) {
                    columnResultSet.close();
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
