package com.hanshan.common.pojo.param;

import lombok.Data;

@Data
public class JdbcConnectParam {
    private String url;
    private String username;
    private String password;
}
