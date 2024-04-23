package com.hanshan.api.model;

import lombok.Data;

@Data
public class ServerInfo {
    private String serverId;
    private String serverName;
    private String user;
    private String password;
    private Integer port;
    private String host;
    private String serverType;
    private String connectionType;
    /**
     * 实例名称
     */
    private String instanceName;
    private String version;
    /**
     * 登录角色
     * oracle会用到
     */
    private String role;
    /**
     * 记录密码
     */
//    rememberMe?: boolean;
//
//    timezone;
//    connectTimeout?: number;
//    requestTimeout?: number;
//    connectionUrl;
//    /**
//     * ssh
//     */
//    usingSsh?: boolean;
//
//    /**
//     * ssl
//     */
//    useSsl?: boolean;
//    caPath;
//    clientCertPath;
//    clientKeyPath;
//
//    /**
//     * sqlite only
//     */
//    dbPath;
//
//    /**
//     * mssql only
//     */
//    mssqlEncrypt?: boolean;
//
//    mssqlDomain;
//    mssqlAuthType;
//
//    /**
//     * oracle
//     */
//    orclConnType;
//    /**
//     * ServerName||Sid，
//     * 存储在instanceName
//     */
//    orclServerType?: 'Service Name' | 'SID' | string;
//    orclLibPath;
//
//    /**
//     * es only
//     */
//    esScheme;
//    esAuth;
//    esToken;
//    /**
//     * using when ssh tunnel
//     */
//    esUrl;
//
//    /**
//     * encoding, ftp only
//     */
//    encoding;
//    showHidden?: boolean;
//
//    sortNo?: number;
//    /**
//     * 本服务最后一次打开连接时间
//     */
//    lastOpenTime?: string | null; //Date;
//    /**
//     * 发生数据变化时，更新时间
//     * 第一次数据创建，将创建时间插入
//     */
//    updateDate?: string | null; //Date;
//    createDate?: string | null; //Date;
//
//    /**
//     * 是否上传到服务端
//     * 默认为flase
//     */
//    isUpload?: boolean;
//    /**
//     * 首字母
//     */
//    initialLetter;
//    serverClass?: ServerClass;
//    /**
//     * kafka信息
//     */
//    clientId;
//    groupId;


}
