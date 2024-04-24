package com.hanshan.app.model.query;

import com.hanshan.api.query.ConnectQuery;
import lombok.Data;

import java.io.Serializable;
@Data
public class Request<T> implements Serializable {
    private ConnectQuery connect;
    private T data;
}
