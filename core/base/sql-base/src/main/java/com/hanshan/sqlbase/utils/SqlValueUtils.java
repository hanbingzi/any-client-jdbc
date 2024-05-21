package com.hanshan.sqlbase.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlValueUtils {
    public final static Logger logger = LoggerFactory.getLogger(SqlValueUtils.class.getName());

    public static String resolveVFTSPName(String functionName) {
        if (StringUtils.isEmpty(functionName)) return functionName;
        int semicolonIndex = functionName.indexOf(";");
        if (semicolonIndex > 0) {
            return functionName.substring(0, semicolonIndex);
        }
        return functionName;
    }

}
