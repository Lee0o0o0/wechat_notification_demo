package com.api.dev.service;

import com.api.dev.utils.WechatUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    public boolean validateToken(String signature,String timestamp,String nonce){
            try{
                return WechatUtils.checkWxToken(signature, timestamp, nonce);
            } catch (Exception e){
                e.printStackTrace();
            }
        return false;
    }
}
