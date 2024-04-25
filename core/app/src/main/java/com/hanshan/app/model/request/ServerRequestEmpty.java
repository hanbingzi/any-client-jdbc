package com.hanshan.app.model.request;

import com.hanshan.api.query.ConnectQuery;
import com.hanshan.app.AppContext;
import com.hanshan.app.exception.NoServerException;
import com.hanshan.app.exception.ParamErrorException;
import com.hanshan.app.model.query.ServerQuery;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class ServerRequestEmpty implements Serializable {
    private ConnectQuery connect;

    public void setConnect(ConnectQuery connect) {
        this.connect = connect;
    }

    public ConnectQuery getConnect() throws ParamErrorException, NoServerException {
        if (this.connect.getServer() == null) {
            String serverId = this.connect.getServerId();
            if (StringUtils.isEmpty(serverId)) {
                //抛出异常
                throw new ParamErrorException();
            } else {
                ServerQuery serverQuery = AppContext.ServerStoreMap.get(serverId);
                if (serverQuery == null || serverQuery.getServer() == null) {
                    //抛出异常
                    throw new NoServerException();
                } else {
                    connect.setServer(serverQuery.getServer());
                    if (serverQuery.getCluster() != null)
                        connect.setCluster(serverQuery.getCluster());
                }
            }
        }
        return this.connect;
    }
}
