package com.api.dev.service;

import com.api.dev.utils.wechatUtils;
import org.springframework.stereotype.Component;

@Component
public class tokenService {

    public boolean validateToken(String signature,String timestamp,String nonce){
            try{
                return wechatUtils.checkWxToken(signature, timestamp, nonce);
            } catch (Exception e){
                e.printStackTrace();
            }
        return false;
    }
}
