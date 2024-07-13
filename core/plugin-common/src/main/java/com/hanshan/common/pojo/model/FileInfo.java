package com.hanshan.common.pojo.model;

import com.hanshan.common.types.JDBCJavaTypes;
import com.hanshan.common.utils.SqlFileUtils;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

@Data
public class FileInfo implements Serializable {
    private static final long MAX_LENGTH = 1024 * 1024;
    /**
     * 长度
     */
    private Long length;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     * Blob
     * Clob
     * Byte[]
     */
    private String type;
    private String data;


    public static FileInfo getInstance(Clob clob) throws SQLException, IOException {
        if (clob != null) {
            FileInfo fileInfo = new FileInfo();
            Long length = clob.length();
            fileInfo.setName("(CLOB) " + length + " bytes");
            fileInfo.setLength(length);
            fileInfo.setType(JDBCJavaTypes._CLOB.getName());
            fileInfo.setData(SqlFileUtils.clobToString(clob, MAX_LENGTH));
            return fileInfo;
        }
        return null;
    }

    public static FileInfo getInstance(Blob blob) throws SQLException, IOException {
        if (blob != null) {
            FileInfo fileInfo = new FileInfo();
            Long length = blob.length();
            fileInfo.setName("(BLOB) " + length + " bytes");
            fileInfo.setLength(length);
            fileInfo.setType(JDBCJavaTypes._BLOB.getName());
            //blob通常存储二进制，所有不要转换
            //fileInfo.setData(SqlFileUtils.blobToString(blob, MAX_LENGTH));
            return fileInfo;
        }
        return null;
    }

    public static FileInfo getInstance(byte[] bytes) {
        if (bytes != null) {
            FileInfo fileInfo = new FileInfo();
            Integer length = bytes.length;
            fileInfo.setName("(BYTE) " + length + " bytes");
            fileInfo.setLength(length.longValue());
            fileInfo.setType(JDBCJavaTypes._BYTES.getName());
            fileInfo.setData(SqlFileUtils.byteArrayToString(bytes, MAX_LENGTH));
            return fileInfo;
        }
        return null;

    }
}
