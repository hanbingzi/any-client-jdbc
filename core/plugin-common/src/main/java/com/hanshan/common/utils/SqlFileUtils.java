package com.hanshan.common.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

public class SqlFileUtils {


    public static String blobToString(Blob blob, long maxLength) throws IOException, SQLException {
        if (blob == null) return null;

        long blobLength = blob.length();
        if (blobLength > maxLength) {
            return null;
        }
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1 && totalBytesRead < maxLength) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            return outputStream.toString(StandardCharsets.UTF_8);
        }
    }
    public static String clobToString(Clob clob, long maxLength) throws SQLException, IOException {
        if (clob == null) return null;

        long clobLength = clob.length();
        if (clobLength > maxLength) {
            return null;
        }

        try (Reader reader = clob.getCharacterStream();
             StringWriter writer = new StringWriter()) {
            char[] buffer = new char[4096];
            int charsRead;
            long totalCharsRead = 0;
            while ((charsRead = reader.read(buffer)) != -1 && totalCharsRead < maxLength) {
                writer.write(buffer, 0, charsRead);
                totalCharsRead += charsRead;
            }
            return writer.toString();
        }
    }

    public static String byteArrayToString(byte[] bytes, long maxLength) {
        if (bytes == null) return null;
        if (bytes.length > maxLength) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }


}
