package com.api.dev.controller;

import com.api.dev.service.MessageService;
import com.api.dev.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/customerAutomessage")
    public String customerMessage(String signature, String timestamp, String nonce, String echostr) {
        if (StringUtils.hasText(echostr) && tokenService.validateToken(signature, timestamp, nonce)) {
            return echostr;
        }
        return "验证失败";
    }

    @PostMapping("/customerAutomessage")
    public String sendCustomerMessage(HttpServletRequest request) {
        return messageService.sendReplyMessage(request);
    }

    @GetMapping("/getAccessToken")
    public String test() {
       return tokenService.getAccessToken();
    }
}
