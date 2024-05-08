package com.hanshan.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CommonTest {


    private static final String ENCRYPTION_KEY = "f3d2e9a4b5c6d7f8a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e323";
    private static final String IV = "f3d2e9a4b5c6d7f8a1b2c3d4e5f6a7b8";

    public static void main(String[] args) throws Exception {
        String encryptedData = "c90cc3adeceaa794d33ea15ff66d3f1d"; // 请替换为实际加密数据
        System.out.println(decryptData(encryptedData));
    }

        public static String decryptData(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(hexStringToByteArray(ENCRYPTION_KEY), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(hexStringToByteArray(IV));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        if (encryptedData.contains("+") || encryptedData.contains("/")) { // 判断是否Base64编码
            encryptedData = new String(Base64.getDecoder().decode(encryptedData), "UTF-8");
        }

        byte[] decodedData = hexStringToByteArray(encryptedData); // 如果数据是十六进制字符串
        byte[] decryptedBytes = cipher.doFinal(decodedData);

        return new String(decryptedBytes, "UTF-8");
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
