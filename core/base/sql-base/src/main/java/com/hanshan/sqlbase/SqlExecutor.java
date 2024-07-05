package com.hanshan.sqlbase;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ColumnMeta;
import com.hanshan.common.pojo.param.SqlPsParam;
import com.hanshan.common.pojo.param.SqlQueryParam;
import com.hanshan.common.pojo.query.BatchSqlQuery;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.query.SqlQuery;
import com.hanshan.common.pojo.result.Result;
import com.hanshan.common.pojo.result.RunSqlResult;
import com.hanshan.common.types.ResponseEnum;
import com.hanshan.common.utils.SqlDateUtils;
import com.hanshan.common.utils.SqlPatternUtils;
import com.hanshan.sqlbase.utils.ResultSetColumnUtils;
import com.hanshan.sqlbase.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);

    public static RunSqlResult<List<Map<String, Object>>> query(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, SqlQuery query, boolean tableQuery) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.runSqlError(connectResult);
        }
        Connection connection = null;
        try {
            long startTime = System.currentTimeMillis();
            connection = connectResult.getData();
            SqlQueryParam sqlQueryParam = null;
            if (tableQuery) {
                sqlQueryParam = SqlQueryParam.getInstance(true, query, connectQuery.getDb(), connectQuery.getSchema(), configurationApi);
            } else {
                sqlQueryParam = SqlUtils.getSqlQuery(connectQuery, configurationApi, query);
            }
            RunSqlResult<List<Map<String, Object>>> sqlResult = query(connection, null, sqlQueryParam);
            Long costTime = System.currentTimeMillis() - startTime;
            sqlResult.setCostTime(costTime);
            return sqlResult;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.runSqlError(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    public static RunSqlResult<List<Map<String, Object>>> query(Connection conn, String sql, SqlQueryParam sqlQuery) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String runSql = StringUtils.isNotEmpty(sql) ? sql : sqlQuery.getSql();
        RunSqlResult<List<Map<String, Object>>> sqlResult = new RunSqlResult<>();
        sqlResult.setIsQuery(true);
        sqlResult.setSql(runSql);
        try {
            ps = conn.prepareStatement(runSql);
            System.out.println("query runSql" + runSql);
            resultSet = ps.executeQuery();
            //解析列
            List<ColumnMeta> columnMetas = ResultSetColumnUtils.getSqlResultColumns(conn, resultSet.getMetaData(), sqlQuery);
            List<Map<String, Object>> data = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> dataItem = new LinkedHashMap<>();
                for (int i = 1; i <= columnMetas.size(); i++) {
                    ColumnMeta meta = columnMetas.get(i - 1);
                    String columnLabel = meta.getLabel();
                    Object value;//= resultSet.getObject(i + 1);
                    //logger.info("name:{},dataType:{},typeName:{}", meta.getLabel(), meta.getDataType(), (value != null ? value.getClass().getTypeName() : ""));
                    //Object convertValue = SqlValueUtils.convertQueryValue(value, meta.getJdbcDataType());
                    //System.out.println("name:" + meta.getName() + ",columnType:" + meta.getColumnType() + ",DataType:" + meta.getDataType() + ",typeName");
                    switch (meta.getJdbcDataType()) {
                        case _BOOLEAN:
                            value = resultSet.getBoolean(i);
                            break;
                        case _BYTE:
                            value = resultSet.getByte(i);
                            break;
                        case _SHORT:
                            value = resultSet.getShort(i);
                            break;
                        case _BIT:
                        case _INT:
                            value = resultSet.getInt(i);
                            break;
                        case _LONG:
                            value =String.valueOf(resultSet.getLong(i));
                           // System.out.println(columnLabel+"-"+value);
                            break;
                        case _FLOAT:
                            value = resultSet.getFloat(i);
                            break;
                        case _DOUBLE:
                            value = resultSet.getDouble(i);
                            break;
                        case _BIGDECIMAL:
                            value = resultSet.getBigDecimal(i);
                            break;
                        case _STRING:
                            value = resultSet.getString(i);
                            break;
                        case _DATE:
                            Date tempDate = resultSet.getDate(i);
                            value = tempDate != null ? SqlDateUtils.getDate(tempDate) : tempDate;
                            break;
                        case _YEAR:
                            Date tempYear = resultSet.getDate(i);
                            value = tempYear != null ? SqlDateUtils.getYear(tempYear) : tempYear;
                            break;
                        case _TIME:
                            java.sql.Time tempTime = resultSet.getTime(i);
                            value = tempTime != null ? SqlDateUtils.getTime(tempTime) : tempTime;
                            break;
                        case _TIMESTAMP:
                            java.sql.Timestamp tempTimestamp = resultSet.getTimestamp(i);
                            value = tempTimestamp != null ? SqlDateUtils.getTimeStamp(tempTimestamp) : tempTimestamp;
                            break;
                        case _BLOB:
                            Blob tempBlob = resultSet.getBlob(i);
                            value = tempBlob != null ? tempBlob.length() : tempBlob;
                            break;
                        case _CLOB:
                            Clob tempClob = resultSet.getClob(i);
                            value = tempClob != null ? "(BLOB) " + tempClob.length() + " bytes" : null;
                            break;
                        case _BYTES:
                            byte[] tempByte = resultSet.getBytes(i);
                            value = tempByte != null ? "(BLOB) " + tempByte.length + " bytes" : null;
                            break;
                        case _URL:
                            java.net.URL tempUrl = resultSet.getURL(i);
                            value = tempUrl != null ? tempUrl.toString() : null;
                            break;
                        default:
                            value = resultSet.getObject(i);
                            break;
                    }
                    dataItem.put(columnLabel, value);
                }
                data.add(dataItem);
            }

            sqlResult.setSuccess(true);
            sqlResult.setMessage("success");
            sqlResult.setCode(ResponseEnum.SUCCESS.code);

            sqlResult.setColumns(columnMetas);
            sqlResult.setData(data);
            return sqlResult;
        } catch (SQLException e) {
            e.printStackTrace();
            sqlResult.setMessage(e.getMessage());
            sqlResult.setSuccess(false);
            sqlResult.setCode(e.getErrorCode());
            return sqlResult;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }


    }


    public static List<RunSqlResult> multiExecute(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, List<String> sqlList, List<List<SqlPsParam>> psParams) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return List.of(Result.runSqlError(connectResult));
        }
        Connection connection = null;
        try {
            List<RunSqlResult> runSqlResultList = new ArrayList<>();
            connection = connectResult.getData();
            for (int i = 0; i < sqlList.size(); i++) {
                long startTime = System.currentTimeMillis();
                String sql = sqlList.get(i);
                List<SqlPsParam> paramList = psParams.get(i);
                RunSqlResult sqlResult = execute(connection, sql, paramList);
                Long costTime = System.currentTimeMillis() - startTime;
                sqlResult.setCostTime(costTime);
                runSqlResultList.add(sqlResult);
            }
            return runSqlResultList;
        } catch (Exception e) {
            return List.of(Result.runSqlError(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage()));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    public static RunSqlResult execute(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, String sql, List<SqlPsParam> psParams) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.runSqlError(connectResult);
        }
        Connection connection = null;
        try {
            long startTime = System.currentTimeMillis();
            connection = connectResult.getData();
            RunSqlResult sqlResult = execute(connection, sql, psParams);
            Long costTime = System.currentTimeMillis() - startTime;
            sqlResult.setCostTime(costTime);
            return sqlResult;
        } catch (Exception e) {
            return Result.runSqlError(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    public static RunSqlResult execute(Connection conn, String sql, List<SqlPsParam> psParams) {
        PreparedStatement ps = null;
        RunSqlResult sqlResult = new RunSqlResult<>();
        sqlResult.setIsQuery(false);
        sqlResult.setSql(sql);
        try {
            ps = conn.prepareStatement(sql);
            Integer affectRow = ps.executeUpdate();
            //解析列
            sqlResult.setSuccess(true);
            sqlResult.setCode(ResponseEnum.SUCCESS.code);

            sqlResult.setAffectedRows(affectRow);
            sqlResult.setMessage("Affected Rows: " + affectRow);
            return sqlResult;
        } catch (SQLException e) {
            e.printStackTrace();
            sqlResult.setSuccess(false);
            sqlResult.setCode(e.getErrorCode());
            sqlResult.setMessage(e.getMessage());
            return sqlResult;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }


    public static List<RunSqlResult> runBatch(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, BatchSqlQuery batchQuery) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return List.of(Result.runSqlError(connectResult));
        }
        List<RunSqlResult> batchSqlResultList = new ArrayList<>();
        Connection connection = null;
        try {
            List<String> batchSql = batchQuery.getBatchSql();
            connection = connectResult.getData();
            for (String sql : batchSql) {
                if (StringUtils.isEmpty(sql)) {
                    continue;
                }
                long startTime = System.currentTimeMillis();
                RunSqlResult sqlResult = null;
                if (SqlPatternUtils.isQuerySql(sql)) {
                    sqlResult = query(connection, null, SqlUtils.getSqlQuery(connectQuery, configurationApi, sql, null, batchQuery.getAlyColumn()));
                } else {
                    sqlResult = execute(connection, sql, null);
                }
                Long costTime = System.currentTimeMillis() - startTime;
                sqlResult.setCostTime(costTime);
                batchSqlResultList.add(sqlResult);
            }
            return batchSqlResultList;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(Result.runSqlError(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage()));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

    public static RunSqlResult runSql(ConnectQuery connectQuery, IJdbcConfiguration configurationApi, String sql) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.runSqlError(connectResult);
        }
        Connection connection = null;
        try {
            connection = connectResult.getData();
            long startTime = System.currentTimeMillis();
            RunSqlResult sqlResult = null;
            if (SqlPatternUtils.isQuerySql(sql)) {
                sqlResult = query(connection, sql, null);
            } else {
                sqlResult = execute(connection, sql, null);
            }
            Long costTime = System.currentTimeMillis() - startTime;
            sqlResult.setCostTime(costTime);
            return sqlResult;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.runSqlError(ResponseEnum.UNKNOWN_ERROR.code, e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("run query error-code{},message{}", e.getErrorCode(), e.getMessage());
            }
        }
    }

}
