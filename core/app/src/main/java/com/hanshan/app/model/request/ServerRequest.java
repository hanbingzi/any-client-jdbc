package com.hanshan.app.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServerRequest<T> extends ServerRequestEmpty {
    private T data;
}
