package com.hanshan.sqlbase;

import com.hanshan.IJdbcConfigurationApi;
import com.hanshan.api.model.ColumnMeta;
import com.hanshan.api.param.SqlPsParam;
import com.hanshan.api.query.ConnectQuery;
import com.hanshan.api.result.Result;
import com.hanshan.api.result.RunSqlResult;
import com.hanshan.common.types.ResponseEnum;
import com.hanshan.common.utils.SqlPatternUtils;
import com.hanshan.sqlbase.utils.ResultSetColumnUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);

    public static RunSqlResult<List<Map<String, Object>>> query(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi, String sql, List<SqlPsParam> psParams) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return Result.runSqlError(connectResult);
        }
        Connection connection = null;
        try {
            long startTime = System.currentTimeMillis();
            connection = connectResult.getData();
            RunSqlResult<List<Map<String, Object>>> sqlResult = query(connection, sql, psParams);
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

    public static RunSqlResult<List<Map<String, Object>>> query(Connection conn, String sql, List<SqlPsParam> psParams) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();
            //解析列
            List<ColumnMeta> columnMetas = ResultSetColumnUtils.getSqlResultColumns(conn, resultSet.getMetaData());
            List<Map<String, Object>> data = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> dataItem = new LinkedHashMap<>();
                for (int i = 0; i < columnMetas.size(); i++) {
                    ColumnMeta meta = columnMetas.get(i);
                    String columnName = meta.getName();
                    Object value = resultSet.getObject(i + 1);
                    if (dataItem.containsKey(columnName)) {
                        int j = 1;
                        String renameColumn = columnName + "(" + j + ")";
                        while (dataItem.containsKey(renameColumn)) {
                            renameColumn = columnName + "(" + j + ")";
                            j++;
                        }
                        dataItem.put(renameColumn, value);
                    } else {
                        dataItem.put(columnName, value);
                    }
                }
                data.add(dataItem);
            }
            RunSqlResult<List<Map<String, Object>>> sqlResult = new RunSqlResult<>();
            sqlResult.setSuccess(true);
            sqlResult.setCode(ResponseEnum.SUCCESS.code);
            sqlResult.setIsQuery(true);
            sqlResult.setSql(sql);
            sqlResult.setColumns(columnMetas);
            sqlResult.setData(data);
            return sqlResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.runSqlError(e.getErrorCode(), e.getMessage());
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


    public static RunSqlResult execute(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi, String sql, List<SqlPsParam> psParams) {
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
        try {
            ps = conn.prepareStatement(sql);
            Integer affectRow = ps.executeUpdate();
            //解析列
            RunSqlResult sqlResult = new RunSqlResult<>();
            sqlResult.setSuccess(true);
            sqlResult.setCode(ResponseEnum.SUCCESS.code);
            sqlResult.setIsQuery(false);
            sqlResult.setSql(sql);
            sqlResult.setAffectedRows(affectRow);
            return sqlResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.runSqlError(e.getErrorCode(), e.getMessage());
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


    public static List<RunSqlResult> runBatch(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi, List<String> batchSql, List<SqlPsParam> psParams) {
        Result<Connection> connectResult = DataSourceFactory.getConnection(connectQuery, configurationApi);
        if (!connectResult.getSuccess()) {
            return List.of(Result.runSqlError(connectResult));
        }
        List<RunSqlResult> batchSqlResultList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connectResult.getData();
            for (String sql : batchSql) {
                if (StringUtils.isEmpty(sql)) {
                    continue;
                }
                long startTime = System.currentTimeMillis();
                RunSqlResult sqlResult = null;
                if (SqlPatternUtils.isQuerySql(sql)) {
                    sqlResult = query(connection, sql, psParams);
                } else {
                    sqlResult = execute(connection, sql, psParams);
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

    public static RunSqlResult runSql(ConnectQuery connectQuery, IJdbcConfigurationApi configurationApi, String sql, List<SqlPsParam> psParams) {
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
                sqlResult = query(connection, sql, psParams);
            } else {
                sqlResult = execute(connection, sql, psParams);
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
