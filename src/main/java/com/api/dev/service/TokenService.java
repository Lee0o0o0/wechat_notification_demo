package com.api.dev.service;

import com.api.dev.utils.WechatUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean validateToken(String signature,String timestamp,String nonce){
            try{
                return WechatUtils.checkWxToken(signature, timestamp, nonce);
            } catch (Exception e){
                e.printStackTrace();
            }
        return false;
    }

    /**
     * 获取 wechat token, 设置失效时间
     * @return access_token
     */
    public String getAccessToken(){
        //先判断是否过期
        //solution1: 使用cache，存储在jvm中，但是可能涉及到机器重启失效等问题
        //solution2: 使用外部缓存，如redis，本例中使用redis
        String token = (String) redisTemplate.opsForValue().get("access_token");
        if (StringUtils.hasText(token)){
            System.out.println("从缓存中获取");
            return token;
        }

        String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential" +
                "&appid=wxe075ce893a244b81"+
                "&secret=bc657501fe2b92d46071706eaf07004c";
        JSONObject content = restTemplate.getForObject(access_token_url, JSONObject.class);
        Map<String, String> tokenData = new HashMap<>();
        for (Object o : content.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) o;
            tokenData.put(entry.getKey(), entry.getValue().toString());
        }

        if (tokenData.containsKey("access_token")){
            System.out.println("重新获取");
            token = tokenData.get("access_token");
            redisTemplate.opsForValue().set("access_token", token, Long.parseLong(tokenData.get("expires_in")), TimeUnit.SECONDS);
            System.out.println("失效时间：" + redisTemplate.getExpire("access_token"));
            return tokenData.get("access_token");
        }
        return tokenData.get("errmsg");
    }
}
