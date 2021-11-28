package com.api.dev.service;

import com.api.dev.model.*;
import com.api.dev.utils.wechatUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.api.dev.utils.wechatUtils.mapToXML;

@Component
public class messageService {

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

    public WeixinResponse sendMessageToAllByOpenId(String accessToken, CustomerMessage customerMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + accessToken;
        return restTemplate.postForObject(url, customerMessage, WeixinResponse.class,new HashMap<String,String>());
    }

    public WeixinResponse sendMessageToAllByTag(String accessToken, ToAllMessage toAllMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + accessToken;
        return restTemplate.postForObject(url, toAllMessage, WeixinResponse.class,new HashMap<String,String>());
    }

    public String sendReplyMessage(HttpServletRequest request) {
        try {
            Map<String,String> requestMap = wechatUtils.parseXml(request);
            String msgType = requestMap.get("MsgType");
            String userMessage = requestMap.get("Content");
            String mes = null;
            if (msgType.equals("text")) {
                switch(userMessage){
                    case "西瓜" :
                        mes = "西瓜当前价格: 8元/1000g";
                        break;
                    case "苹果" :
                        mes = "苹果当前价格: 10元/1000g";
                        break;
                    default :
                        mes = "请输入你想咨询价格的水果名称";
                }

            }
            return generateMsg(mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String  generateMsg(String content){
        Map<String,Object> map= new HashMap<>();
        map.put("ToUserName", "oHgeM50LShy2Hh3H1arsvqsUpNM0");
        map.put("FromUserName", "gh_23aaf18c8ac5");
        map.put("MsgType", "text");
        map.put("CreateTime", new Date().getTime());
        map.put("Content", content);
        return mapToXML(map);
    }
}
