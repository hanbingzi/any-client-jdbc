package com.hanshan.sqlbase.utils;

import com.hanshan.api.param.SqlPsParam;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StatementUtils {
    public static PreparedStatement fillParams(PreparedStatement ps, List<SqlPsParam> params) throws SQLException {
        if (null == params || params.isEmpty()) {
            return ps;
        }
        for (SqlPsParam param : params) {
            setParam(ps, param);
        }

        return null;
    }

    public static void setParam(PreparedStatement ps, SqlPsParam param) throws SQLException {
        String type = param.getType();
        Object value = param.getValue();
        JDBCType jdbcType = JDBCType.valueOf(type);
        switch (jdbcType){
            case BIT -> {

            }
            case TINYINT -> {
            }
            case SMALLINT -> {
            }
            case INTEGER -> {
            }
            case BIGINT -> {
            }
            case FLOAT -> {
            }
            case REAL -> {
            }
            case DOUBLE -> {
            }
            case NUMERIC -> {
            }
            case DECIMAL -> {
            }
            case CHAR -> {
            }
            case VARCHAR -> {
            }
            case LONGVARCHAR -> {
            }
            case DATE -> {
            }
            case TIME -> {
            }
            case TIMESTAMP -> {
            }
            case BINARY -> {
            }
            case VARBINARY -> {
            }
            case LONGVARBINARY -> {
            }
            case NULL -> {
            }
            case OTHER -> {
            }
            case JAVA_OBJECT -> {
            }
            case DISTINCT -> {
            }
            case STRUCT -> {
            }
            case ARRAY -> {
            }
            case BLOB -> {
            }
            case CLOB -> {
            }
            case REF -> {
            }
            case DATALINK -> {
            }
            case BOOLEAN -> {
            }
            case ROWID -> {
            }
            case NCHAR -> {
            }
            case NVARCHAR -> {
            }
            case LONGNVARCHAR -> {
            }
            case NCLOB -> {
            }
            case SQLXML -> {
            }
            case REF_CURSOR -> {
            }
            case TIME_WITH_TIMEZONE -> {
            }
            case TIMESTAMP_WITH_TIMEZONE -> {
            }
        }

    }

}
