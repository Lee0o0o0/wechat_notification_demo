package com.api.dev.utils;

import org.dom4j.Document;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.*;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WechatUtils {

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
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
        Map<String,String> map = new HashMap<>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        inputStream.close();
        return map;
    }

    public static String mapToXML(Map<String,Object> map) {
        StringBuilder sb = new StringBuilder("<xml>");
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            sb.append("<").append(key).append(">");
            sb.append(value);
            sb.append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
