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

//    connectionType?:AllConnectionType;
//    /**
//     * 实例名称
//     */
    private String instanceName;
    private String version;
//    /**
//     * 登录角色
//     * oracle会用到
//     */
    private String role;
//    /**
//     * 记录密码
//     */
//    rememberMe?:boolean;
//
//    timezone?:string;
//    connectTimeout?:number;
//    requestTimeout?:number;
//    connectionUrl?:string;
//    /**
//     * ssh
//     */
//    usingSsh?:boolean;
//
//    /**
//     * ssl
//     */
//    useSsl?:boolean;
//    caPath?:string;
//    clientCertPath?:string;
//    clientKeyPath?:string;
//
//    /**
//     * sqlite only
//     */
//    dbPath?:string;
//
//    /**
//     * mssql only
//     */
//    mssqlEncrypt?:boolean;
//
//    mssqlDomain?:string;
//    mssqlAuthType?:string;
//
//    /**
//     * oracle
//     */
//    orclConnType?:string;
//    /**
//     * ServerName||Sid，
//     * 存储在instanceName
//     */
//    orclServerType?:'Service Name'|'SID'|string;
//    orclLibPath?:string;
//
//    /**
//     * es only
//     */
//    esScheme?:string;
//    esAuth?:string;
//    esToken?:string;
//    /**
//     * using when ssh tunnel
//     */
//    esUrl?:string;
//
//    /**
//     * encoding, ftp only
//     */
    private String encoding;
//    showHidden?:boolean;
//
//    sortNo?:number;
//    /**
//     * 本服务最后一次打开连接时间
//     */
//    lastOpenTime?:string |null; //Date;
//    /**
//     * 发生数据变化时，更新时间
//     * 第一次数据创建，将创建时间插入
//     */
//    updateDate?:string |null; //Date;
//    createDate?:string |null; //Date;
//
//    /**
//     * 是否上传到服务端
//     * 默认为flase
//     */
//    isUpload?:boolean;
//    /**
//     * 首字母
//     */
//    initialLetter?:string;
//    serverClass?:ServerClass;
//    /**
//     * kafka信息
//     */
//    clientId?:string;
//    groupId?:string;
}
