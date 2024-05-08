package com.hanshan.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlPatternUtils {
        public static boolean  isQuerySql(String sql) {
            // 正则表达式，匹配以SELECT、WITH、EXPLAIN开头的语句，不区分大小写
            String regex = "^(?i)(SELECT|WITH|EXPLAIN).*";
            // 使用Pattern和Matcher进行匹配
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sql.trim());
            // 如果找到匹配项，说明是查询语句
            return matcher.find();
        }
}
