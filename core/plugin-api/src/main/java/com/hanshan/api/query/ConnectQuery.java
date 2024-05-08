package com.hanshan.api.query;

import com.hanshan.api.model.DbInfo;
import com.hanshan.api.model.ServerCluster;
import com.hanshan.api.model.ServerInfo;
import lombok.Data;

@Data
public class ConnectQuery {

    private ServerInfo server;
    private ServerCluster[] cluster;
    /**
     * 传serverId，就不用传server
     */
    private String serverId;
    //  ssh?: SSHConfig;
    /**
     * 数据库
     */
    private String db;
    private String schema;
    /**
     * 密码是否加密
     */
    private Boolean originPassword;

    public DbInfo getDbInfo() {
        return new DbInfo(this.getDb(), this.getSchema());
    }

}
