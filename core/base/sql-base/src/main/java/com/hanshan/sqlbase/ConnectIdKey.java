package com.hanshan.sqlbase;

import com.hanshan.api.model.ServerInfo;
import com.hanshan.api.query.ConnectQuery;
import org.apache.commons.lang3.StringUtils;

public class ConnectIdKey {
    public static String getConnectIdKey(ConnectQuery connectQuery) {
        ServerInfo server = connectQuery.getServer();
        String db = connectQuery.getDb();
        String schema = connectQuery.getSchema();
        StringBuilder idKey = new StringBuilder();
        if (StringUtils.isNotEmpty(server.getServerId())) {
            idKey.append(server.getServerId()).append("/");
        }
        idKey.append(server.getServerType()).append("/").append(server.getHost()).append("@").append(server.getPort());
        if (StringUtils.isNotEmpty(server.getUser())) {
            idKey.append("/").append(server.getUser());
        }
        if (StringUtils.isNotEmpty(db)) {
            idKey.append("/").append(db);
        }
        if (StringUtils.isNotEmpty(schema)) {
            idKey.append("/").append(schema);
        }
        return idKey.toString();
    }
}
