package com.hanshan.app.model.query;

import com.hanshan.api.model.ServerCluster;
import com.hanshan.api.model.ServerInfo;
import lombok.Data;

@Data
public class ServerQuery {

    private ServerInfo server;
    private ServerCluster[] cluster;
}
