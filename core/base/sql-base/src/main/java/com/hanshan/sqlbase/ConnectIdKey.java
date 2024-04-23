package com.hanshan.sqlbase;

import com.hanshan.api.model.ServerInfo;
import com.hanshan.api.query.ConnectQuery;

public class ConnectIdKey {
    public static String getConnectKey(ConnectQuery connectQuery) {
        ServerInfo server = connectQuery.getServer();
        return server.getServerType();
    }
}
