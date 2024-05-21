package com.hanshan.app.model.query;

import com.hanshan.common.pojo.model.ServerCluster;
import com.hanshan.common.pojo.model.ServerInfo;
import lombok.Data;

@Data
public class ServerQuery {

    private ServerInfo server;
    private ServerCluster[] cluster;
}
