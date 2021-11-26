package com.api.dev.utils;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Formatter;

public class wechatUtils {

    public static boolean checkWxToken(String signature, String timestamp, String nonce) throws Exception{
        if (!StringUtils.hasText(signature) || !StringUtils.hasText(timestamp) || !StringUtils.hasText(nonce)) {
            return false;
        }
        String token = "LeeTest123456";
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);
        String strTmp = String.join("", arr);
        strTmp = toSha1(strTmp);
        return signature.equals(strTmp);
    }

    private static String toSha1(String str) throws Exception{
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash){
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
