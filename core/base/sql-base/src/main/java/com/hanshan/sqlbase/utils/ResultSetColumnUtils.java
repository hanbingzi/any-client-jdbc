package com.hanshan.sqlbase.utils;

import com.hanshan.api.model.ColumnMeta;
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
    public static List<ColumnMeta> getSqlResultColumns(Connection conn, ResultSetMetaData metaData) throws SQLException {
        int columnCount = metaData.getColumnCount();
        List<ColumnMeta> columnMetaList = new ArrayList<>();
        Map<String, List<String>> primaryMap = new HashMap<>();
        List<String> labelList = new ArrayList<>();
        //处理column
        for (int i = 1; i <= columnCount; i++) {
            String dbName = metaData.getCatalogName(i);
            String schemaName = metaData.getSchemaName(i);
            String tableName = metaData.getTableName(i);
            String columnName = metaData.getColumnName(i);
            ColumnMeta columnMeta = new ColumnMeta();
            columnMeta.setName(columnName);
            String label = columnName;
            if (labelList.contains(label)) {
                int j = 1;
                label = label + "(" + j + ")";
                while (labelList.contains(label)) {
                    label = label + "(" + j + ")";
                    j++;
                }
            }
            labelList.add(label);
            columnMeta.setLabel(label);
            //String className = metaData.getColumnClassName(i);
            columnMeta.setDbName(dbName);
            columnMeta.setSchemaName(schemaName);
            columnMeta.setTableName(tableName);
            //Integer columnDisplaySize = metaData.getColumnDisplaySize(i);
            columnMeta.setColumnType(metaData.getColumnTypeName(i));
            //columnMeta.setLength( metaData.getPrecision(i));;
            columnMeta.setLength(metaData.getColumnDisplaySize(i));
            columnMeta.setScale(metaData.getScale(i));
            Integer columnType = metaData.getColumnType(i);
            JDBCJavaTypes jdbcColumnType = JDBCJavaTypes.getTypeById(columnType);
            columnMeta.setDataType(jdbcColumnType.getName());
            //处理备注，
            if (StringUtils.isNotEmpty(tableName)) {
                ColumnMeta extraMeta = getColumnMore(conn, dbName, schemaName, tableName, columnName);
                if (extraMeta != null) {
                    columnMeta.setComment(extraMeta.getComment());
                    columnMeta.setDefaultValue(extraMeta.getDefaultValue());
                }
                if (!primaryMap.containsKey(tableName)) {
                    primaryMap.put(tableName, getTablePrimaryKeys(conn, dbName, schemaName, tableName));
                }
                List<String> tablePrimary = primaryMap.get(tableName);
                columnMeta.setIsPrimary(tablePrimary.contains(columnName));
            }
            //处理主键
            columnMetaList.add(columnMeta);
        }
        return columnMetaList;
    }


    public static List<ColumnMeta> getColumnInfo(Connection conn, String db, String schema, String table) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet columnResultSet = dbMetaData.getColumns(db, schema, table, null);
        List<ColumnMeta> metas = new ArrayList<>();
        while (columnResultSet.next()) {
            metas.add(resolveColumnInfo(columnResultSet));
        }
        columnResultSet.close();
        return metas;
    }

    public static List<String> getTablePrimaryKeys(Connection conn, String db, String schema, String table) throws SQLException {
        List<String> primaryList = new ArrayList<>();
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet primaryResultSet = dbMetaData.getPrimaryKeys(db, schema, table);
        while (primaryResultSet.next()) {
            primaryList.add(resolvePrimaryKey(primaryResultSet));
        }
        primaryResultSet.close();
        return primaryList;
    }

    public static ColumnMeta getColumnMore(Connection conn, String db, String schema, String table, String column) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet columnResultSet = dbMetaData.getColumns(db, schema, table, column);
        ColumnMeta meta = null;
        while (columnResultSet.next()) {
            meta = resolveColumnInfo(columnResultSet);
        }
        columnResultSet.close();
        return meta;
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
     * @return
     * @throws SQLException
     */
    public static ColumnMeta resolveColumnInfo(ResultSet columnResultSet) throws SQLException {
        ColumnMeta meta = new ColumnMeta();
        // Boolean isPrimaryKey = columnResultSet.getBoolean("PRIMARY_KEY");
        String comment = columnResultSet.getString("REMARKS");
        Object defaultValue = columnResultSet.getObject("COLUMN_DEF");
        //  meta.setIsPrimary(isPrimaryKey);
        meta.setComment(comment);
        meta.setDefaultValue(defaultValue);
        return meta;
    }

    public static String resolvePrimaryKey(ResultSet columnResultSet) throws SQLException {
        return columnResultSet.getString("COLUMN_NAME");
    }
}
