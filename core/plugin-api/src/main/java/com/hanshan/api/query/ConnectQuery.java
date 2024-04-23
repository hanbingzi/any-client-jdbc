package com.hanshan.api.query;

import com.hanshan.api.model.ServerCluster;
import com.hanshan.api.model.ServerInfo;
import lombok.Data;

@Data
public class ConnectQuery {

    private ServerInfo server;
    private ServerCluster[] cluster;
    //  ssh?: SSHConfig;
    /**
     * 数据库
     */
    private String db;
    private String schema;
}
