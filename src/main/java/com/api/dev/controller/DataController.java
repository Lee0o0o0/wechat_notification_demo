package com.api.dev.controller;

import com.api.dev.service.messageService;
import com.api.dev.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
public class DataController {

    @Autowired
    private messageService messageService;

    @Autowired
    private tokenService tokenService;

    @GetMapping("/sendTemplateMessage")
    public String getUser() {
       return "*****" + Math.random();
    }

    @GetMapping("/customerAutomessage")
    public String customerMessage(String signature,String timestamp,String nonce, String echostr){
        if (StringUtils.hasText(echostr) && tokenService.validateToken(signature, timestamp, nonce)) {
                return echostr;
            }
        return "验证失败";
    }

    @PostMapping("/customerAutomessage")
    public String sendCustomerMessage(HttpServletRequest request) {
        return messageService.sendReplyMessage(request);
    }



}
