package com.api.dev.controller;


import com.api.dev.model.CustomerMessage;
import com.api.dev.model.CustomerMessageContent;
import com.api.dev.model.WeixinResponse;
import com.api.dev.service.sendMessageService;
import com.api.dev.utils.wechatUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    private sendMessageService service;

    @GetMapping("/sendTemplateMessage")
    public String getUser() {
       return "*****" + Math.random();
    }

    @GetMapping("customerAutomessage")
    public String customerMessage(String signature,String timestamp,String nonce, String echostr){
        System.out.println("enter send message");
        if (StringUtils.hasText(echostr)) {
            boolean tokenOk = false;
            try{
                tokenOk = wechatUtils.checkWxToken(signature, timestamp, nonce);
            } catch (Exception e){
                e.printStackTrace();
            }
            if (tokenOk) {
                return echostr;
            }
        }
        return null;
    }

    @PostMapping(value="customerAutomessage")
    public void sendCustomerMessage(Object o) {
        System.out.println("body" + o.toString());
        String token = "51_0okx4MiFi4yN3wY6wCLOo04GVD9M3w48tVa4Nm-cL6AzRQKJ4aOD_n7FwzJV9IrgJUO0i6HxrGIoAq2MOfiEkOBz2gkj3RxsCoUH6udC_dB10yQFjXHpv7p7hOmg6WzaOTa0ooF0CP-kBwOWDHBgABAZDX";
        CustomerMessage templateMessage = new CustomerMessage();
        templateMessage.setTouser("oHgeM50LShy2Hh3H1arsvqsUpNM0");
        templateMessage.setMsgtype("text");
        templateMessage.setText(CustomerMessageContent.builder().content("test send message to user").build());
        WeixinResponse weixinResponse = service.sendMessageToUser(token, templateMessage);
    }


    @RequestMapping("/cgi")
    public void cgi(HttpServletRequest request, HttpServletResponse response) {
        boolean isGet = request.getMethod().equalsIgnoreCase("get");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        try {
            if (isGet) {
                String signature = request.getParameter("signature");
                // 时间戳
                String timestamp = request.getParameter("timestamp");
                // 随机数
                String nonce = request.getParameter("nonce");
                // 随机字符串
                String echostr = request.getParameter("echostr");

                boolean tokenOk = false;
                try{
                    tokenOk = wechatUtils.checkWxToken(signature, timestamp, nonce);
                } catch (Exception e){
                    e.printStackTrace();
                }
                if (tokenOk) {
                    response.getOutputStream().write(echostr.getBytes());
                }
            }else{
                System.out.println("body" + request.toString());
                String token = "51_0okx4MiFi4yN3wY6wCLOo04GVD9M3w48tVa4Nm-cL6AzRQKJ4aOD_n7FwzJV9IrgJUO0i6HxrGIoAq2MOfiEkOBz2gkj3RxsCoUH6udC_dB10yQFjXHpv7p7hOmg6WzaOTa0ooF0CP-kBwOWDHBgABAZDX";
                CustomerMessage templateMessage = new CustomerMessage();
                templateMessage.setTouser("oHgeM50LShy2Hh3H1arsvqsUpNM0");
                templateMessage.setMsgtype("text");
                templateMessage.setText(CustomerMessageContent.builder().content("test send message to user").build());
                WeixinResponse weixinResponse = service.sendMessageToUser(token, templateMessage);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
