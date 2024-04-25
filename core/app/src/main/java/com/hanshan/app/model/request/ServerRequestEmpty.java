package com.hanshan.app.model.request;

import com.hanshan.api.query.ConnectQuery;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServerRequestEmpty implements Serializable {
    private ConnectQuery connect;
}
