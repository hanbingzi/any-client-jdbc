package com.hanshan.app.model.request;

import lombok.Data;

@Data
public class ServerRequest<T> extends ServerRequestEmpty {
    private T data;
}
