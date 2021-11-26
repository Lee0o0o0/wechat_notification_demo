package com.api.dev.service;

import com.api.dev.model.*;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class sendMessageService {

    @Autowired
    private RestTemplate restTemplate ;

    public WeixinResponse sendTemplateMessage(String accessToken, WeChatTemplateMessage weixinTemplate) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
        return restTemplate.postForObject(url, weixinTemplate, WeixinResponse.class,new HashMap<String,String>());
    }

    public WeixinResponse sendMessageToUser(String accessToken, CustomerMessage customerMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
        return restTemplate.postForObject(url, customerMessage, WeixinResponse.class,new HashMap<String,String>());
    }

    //没权限
    public WeixinResponse sendMessageToAllByOpenId(String accessToken, CustomerMessage customerMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + accessToken;
        return restTemplate.postForObject(url, customerMessage, WeixinResponse.class,new HashMap<String,String>());
    }

    //没权限
    public WeixinResponse sendMessageToAllByTag(String accessToken, ToAllMessage toAllMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + accessToken;
        return restTemplate.postForObject(url, toAllMessage, WeixinResponse.class,new HashMap<String,String>());
    }
}
