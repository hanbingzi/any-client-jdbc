package com.hanshan.sqlbase.utils;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.model.ColumnMeta;
import com.hanshan.common.pojo.param.SqlQueryParam;
import com.hanshan.common.types.JDBCJavaTypes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetColumnUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResultSetColumnUtils.class);


    /**
     * sql 查询的列展示
     *
     * @param conn
     * @param metaData
     * @return
     * @throws SQLException
     */
    public static List<ColumnMeta> getSqlResultColumns(Connection conn, ResultSetMetaData metaData, SqlQueryParam sqlQuery) throws SQLException {
        int columnCount = metaData.getColumnCount();
        List<ColumnMeta> columnMetaList = new ArrayList<>();
        Map<String, List<String>> primaryMap = new HashMap<>();
        List<String> labelList = new ArrayList<>();
        //处理column
        for (int i = 1; i <= columnCount; i++) {
            //处理常用的
            ColumnMeta columnMeta = resolveColumn(metaData, i);
            //处理db
            resolveDbAndSchemaAndTable(columnMeta, sqlQuery);
            String dbName = columnMeta.getDbName();
            String schemaName = columnMeta.getSchemaName();
            String tableName = columnMeta.getTableName();
            //处理重复命名
            String columnName = columnMeta.getName();
            String label = columnName;
            if (labelList.contains(label)) {
                int j = 1;
                label = label + "(" + j + ")";
                while (labelList.contains(label)) {
                    label = label + "(" + j + ")";
                    j++;
                }
                columnMeta.setLabel(label);
            }
            labelList.add(label);
            if (sqlQuery.getAlyColumn()) {
                //处理备注，
                resolveOneColumnRemarkAndDef(columnMeta, conn, dbName, schemaName, tableName, columnName, sqlQuery.getConfig());
               if(StringUtils.isNotEmpty(tableName)) {
                   if (!primaryMap.containsKey(tableName)) {
                       primaryMap.put(tableName, getTablePrimaryKeys(conn, dbName, schemaName, tableName, sqlQuery.getConfig()));
                   }
                   List<String> tablePrimary = primaryMap.get(tableName);
                   columnMeta.setIsPrimary(tablePrimary.contains(columnName));
               }
            }
            //处理主键
            columnMetaList.add(columnMeta);

        }
        return columnMetaList;
    }

    public static ColumnMeta resolveColumn(ResultSetMetaData metaData, Integer index) throws SQLException {
        String dbName = metaData.getCatalogName(index);
        String schemaName = metaData.getSchemaName(index);
        String tableName = metaData.getTableName(index);
        String columnName = metaData.getColumnName(index);
        ColumnMeta columnMeta = new ColumnMeta();
        columnMeta.setName(columnName);
        columnMeta.setLabel(columnName);
        //String className = metaData.getColumnClassName(i);
        columnMeta.setDbName(dbName);
        columnMeta.setSchemaName(schemaName);
        columnMeta.setTableName(tableName);
        //Integer columnDisplaySize = metaData.getColumnDisplaySize(i);
        String columnTypeName = metaData.getColumnTypeName(index);
        columnMeta.setColumnType(columnTypeName);
        //columnMeta.setLength( metaData.getPrecision(i));;
        columnMeta.setLength(metaData.getColumnDisplaySize(index));
        columnMeta.setScale(metaData.getScale(index));
        Integer columnType = metaData.getColumnType(index);
        columnMeta.setColumnTypeId(columnType);
        JDBCJavaTypes jdbcColumnType = JDBCJavaTypes.getType(columnType, columnTypeName);
        columnMeta.setDataType(jdbcColumnType.getName());
        columnMeta.setJdbcDataType(jdbcColumnType);
        logger.info("columnName:{},dataType:{},columnTypeName:{},columnTypeId:{}",
                columnName, jdbcColumnType.getName(), columnTypeName, columnType);
        return columnMeta;
    }

    public static void resolveDbAndSchemaAndTable(ColumnMeta meta, SqlQueryParam sqlQuery) {
        if (sqlQuery != null && sqlQuery.getIsSingleTable()) {
            String db = sqlQuery.getDb();
            String schema = sqlQuery.getSchema();
            String table = sqlQuery.getTable();
            if (StringUtils.isEmpty(meta.getDbName()) && StringUtils.isNotEmpty(db)) {
                meta.setDbName(db);
            }
            if (StringUtils.isEmpty(meta.getSchemaName()) && StringUtils.isNotEmpty(schema)) {
                meta.setSchemaName(schema);
            }
            if (StringUtils.isEmpty(meta.getTableName()) && StringUtils.isNotEmpty(table)) {
                meta.setTableName(table);
            }
        }

    }


//    public static List<ColumnMeta> getColumnInfo(Connection conn, String db, String schema, String table) throws SQLException {
//        DatabaseMetaData dbMetaData = conn.getMetaData();
//        ResultSet columnResultSet = dbMetaData.getColumns(db, schema, table, null);
//        List<ColumnMeta> metas = new ArrayList<>();
//        while (columnResultSet.next()) {
//            metas.add(resolveColumnRemarkAndDef(columnResultSet));
//        }
//        columnResultSet.close();
//        return metas;
//    }

    public static List<String> getTablePrimaryKeys(Connection conn, String db, String schema, String table, IJdbcConfiguration config) throws SQLException {

        List<String> primaryList = new ArrayList<>();
        if (config.hasDatabase() && StringUtils.isEmpty(db)) {
            return primaryList;
        } else if (config.hasSchema() && StringUtils.isEmpty(schema)) {
            return primaryList;
        } else if (StringUtils.isEmpty(table)) {
            return primaryList;
        }
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet primaryResultSet = dbMetaData.getPrimaryKeys(db, schema, table);
        while (primaryResultSet.next()) {
            primaryList.add(resolvePrimaryKey(primaryResultSet));
        }
        primaryResultSet.close();
        return primaryList;
    }

    public static void resolveOneColumnRemarkAndDef(ColumnMeta meta, Connection conn, String db, String schema, String table, String column, IJdbcConfiguration config) throws SQLException {
        //验证能不能获取字段备注，
        if (config.hasDatabase() && StringUtils.isEmpty(db)) {
            return;
        }
        if (config.hasSchema() && StringUtils.isEmpty(schema)) {
            return;
        }
        if (StringUtils.isEmpty(table)) {
            return;
        }
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet columnResultSet = dbMetaData.getColumns(db, schema, table, column);
        while (columnResultSet.next()) {
            resolveColumnRemarkAndDef(meta, columnResultSet);
        }
        columnResultSet.close();

    }

    /**
     * COLUMN_NAME - 列的名称
     * TYPE_NAME - 列的类型名称
     * COLUMN_SIZE - 列的大小
     * BUFFER_LENGTH - 用于存储列值的字节数
     * DECIMAL_DIGITS - 小数部分的位数
     * NUM_PREC_RADIX - 数值精度基数
     * NULLABLE - 是否允许列值为 NULL
     * IS_NULLABLE - 列是否可以为 NULL 的描述字符串
     * COLUMN_DEF - 列的默认值
     * SQL_DATA_TYPE - JDBC SQL 类型
     * SQL_DATETIME_SUB - 如果 DATA_TYPE 是日期/时间类型，表示子类型
     * CHAR_OCTET_LENGTH - 列的最大字节长度
     * ORDINAL_POSITION - 列在表中的位置
     * IS_AUTOINCREMENT - 描述列是否自动递增
     * IS_GENERATEDCOLUMN - 描述列是否由数据库自动生成
     *
     * @param columnResultSet
     * @throws SQLException
     */
    public static void resolveColumnRemarkAndDef(ColumnMeta meta, ResultSet columnResultSet) throws SQLException {
        // Boolean isPrimaryKey = columnResultSet.getBoolean("PRIMARY_KEY");
        String comment = columnResultSet.getString("REMARKS");
        Object defaultValue = columnResultSet.getObject("COLUMN_DEF");
        //  meta.setIsPrimary(isPrimaryKey);
        meta.setComment(comment);
        meta.setDefaultValue(defaultValue);
    }

    public static String resolvePrimaryKey(ResultSet columnResultSet) throws SQLException {
        return columnResultSet.getString("COLUMN_NAME");
    }
}
