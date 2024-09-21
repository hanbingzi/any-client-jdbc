package com.hanshan.sqlbase.utils;

import com.hanshan.common.config.IJdbcConfiguration;
import com.hanshan.common.pojo.param.SqlPsParam;
import com.hanshan.common.pojo.param.SqlQueryParam;
import com.hanshan.common.pojo.query.ConnectQuery;
import com.hanshan.common.pojo.query.SqlQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtils {
    public static String TABLE_PATTERN = "\\b(from|join|update|into)\\b\\s*\\[?((\\w|\\.|-|`|\"|')+)\\]?";

    public static String SELECT_TABLE_PATTERN = "\\b(from|join)\\b\\s*\\[?((\\w|\\.|-|`|\"|')+)\\]?";
    public static String DML_PATTERN = "\\b(update|into)\\b\\s*`{0,1}(\\w|\\.|-)+`{0,1}";
    public static String MULTI_PATTERN = "/\b(TRIGGER|PROCEDURE|FUNCTION)\b/gi";

    public static SqlQueryParam getSqlQuery(ConnectQuery connectQuery, IJdbcConfiguration config, SqlQuery query) {
      return  getSqlQuery(connectQuery,config,query.getSql(),query.getParams(),query.getAlyColumn());
    }
    //获取表名
    public static SqlQueryParam getSqlQuery(ConnectQuery connectQuery, IJdbcConfiguration config, String sql,List<SqlPsParam> params,Boolean alyColumn) {
        SqlQueryParam sqlQueryParam = new SqlQueryParam();
        sqlQueryParam.setSql(sql);
        List<String> tables = extractTables(sql);
        if(StringUtils.isNotEmpty(connectQuery.getDb())){
            sqlQueryParam.setDb(connectQuery.getDb());
        }
        if(StringUtils.isNotEmpty(connectQuery.getSchema())){
            sqlQueryParam.setSchema(connectQuery.getSchema());
        }else if(config.hasSchema()){
            sqlQueryParam.setSchema(config.getDefaultSchema());
        }
        if (tables.size() == 1) {
            sqlQueryParam.setIsSingleTable(true);
            String fullTable = tables.get(0);
            String[] tableDb = fullTable.split("\\.");
            if(tableDb.length==1){
                sqlQueryParam.setTable(fullTable);
            }else{
                if(config.hasSchema()){
                    sqlQueryParam.setSchema(tableDb[0]);
                    sqlQueryParam.setTable(tableDb[1]);
                }else{
                    sqlQueryParam.setDb(tableDb[0]);
                    sqlQueryParam.setTable(tableDb[1]);
                }
            }
        }
        sqlQueryParam.setConfig(config);
        sqlQueryParam.setParams(params);
        sqlQueryParam.setAlyColumn(alyColumn);
        sqlQueryParam.setServerType(connectQuery.getServer().getServerType());
        //以sql中的db或者schema为标准
        return sqlQueryParam;
    }

    public static List<String> extractTables(String sql) {
        List<String> tables = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(FROM|JOIN)\\b\\s+([\\w\\d_]+)", Pattern.CASE_INSENSITIVE);
        //Pattern pattern = Pattern.compile("\\b(FROM|JOIN)\\b\\s*\\[?((\\w|\\.|-|`|\"|')+)]?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String tableName = matcher.group(2);
            tables.add(tableName);
        }

        return tables;
    }
}
