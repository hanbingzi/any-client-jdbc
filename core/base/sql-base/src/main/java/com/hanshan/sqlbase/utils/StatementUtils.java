package com.hanshan.sqlbase.utils;

import com.hanshan.common.pojo.param.SqlPsParam;

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
        switch (jdbcType) {
            case BIT:
                break;
            case TINYINT:
                break;
            case SMALLINT:
                break;
            case INTEGER:
                break;
            case BIGINT:
                break;
            case FLOAT:
                break;
            case REAL:
                break;
            case DOUBLE:
                break;
            case NUMERIC:
                break;
            case DECIMAL:
                break;
            case CHAR:
                break;
            case VARCHAR:
                break;
            case LONGVARCHAR:
                break;
            case DATE:
                break;
            case TIME:
                break;
            case TIMESTAMP:
                break;
            case BINARY:
                break;
            case VARBINARY:
                break;
            case LONGVARBINARY:
                break;
            case NULL:
                break;
            case OTHER:
                break;
            case JAVA_OBJECT:
                break;
            case DISTINCT:
                break;
            case STRUCT:
                break;
            case ARRAY:
                break;
            case BLOB:
                break;
            case CLOB:
                break;
            case REF:
                break;
            case DATALINK:
                break;
            case BOOLEAN:
                break;
            case ROWID:
                break;
            case NCHAR:
                break;
            case NVARCHAR:
                break;
            case LONGNVARCHAR:
                break;
            case NCLOB:
                break;
            case SQLXML:
                break;
            case REF_CURSOR:
                break;
            case TIME_WITH_TIMEZONE:
                break;
            case TIMESTAMP_WITH_TIMEZONE:
                break;
        }

    }

}
