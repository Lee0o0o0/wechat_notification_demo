package com.api.dev;

import com.api.dev.model.*;
import com.api.dev.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TemplateTest {

    @Autowired
    private MessageService service;

    @Test
    public void sendTemplateMessageTest() {
        String token = "51_yfmEJHNwh5GheRDhLVrShVx6ZVA_qKYi5YFChLNslytkSz8ltwh5flqXvt7z_Z6FLGSJa0ArtkxrCGVvPxuTfDl1IKpqk5kUoKKoOcbU9nyaPaBlkbGtg1GKJ3qnJJ0NADxthZCXckv5W12TWUPiAAADBW";
        WeChatTemplateMessage templateMessage = new WeChatTemplateMessage();
        templateMessage.setTouser("oHgeM50LShy2Hh3H1arsvqsUpNM0");
        templateMessage.setTemplate_id("1L6guLyfx-nS4v8J_LoG8FLcC_7DIrrRG247lmqjpKY");

        Map<String, TemplateData> data = new HashMap<>();
        data.put("title", TemplateData.builder().value("恭喜下单成功！").color("#173177").build());
        data.put("goods", TemplateData.builder().value("西瓜").color("#173177").build());
        data.put("price", TemplateData.builder().value("18.9元").color("#173177").build());
        data.put("time", TemplateData.builder().value("2021年11月25日").color("#173177").build());
        data.put("end", TemplateData.builder().value("欢迎再次购买！！").color("#173177").build());
        templateMessage.setData(data);
        WeixinResponse weixinResponse = service.sendTemplateMessage(token, templateMessage);

        System.out.println("send message result : " + weixinResponse.getErrmsg());
    }

    @Test
    public void sendMessageToAllUserByOPenIDTest() {
        String token = "51_2hxfxTpLfB8HjDc8IvMjn-14USALPiCVA6fYqep26EcGTj8Earr8di5amLsQ3BoKwr3nDCC2F1fAaUU4HMTx969DBz74sW5z7RtmpgXPimp8VFaq35z9NNc7Z8ta98re4rOD0f2V2aLW6rz3RJOiAJAHZE";
        CustomerMessage templateMessage = new CustomerMessage();
        templateMessage.setTouser(Arrays.asList("oHgeM50LShy2Hh3H1arsvqsUpNM0","oHgeM54IyYCw62NlfQnQLuQWRaSU"));
        templateMessage.setMsgtype("text");
        templateMessage.setText(CustomerMessageContent.builder().content("test send user text message by OpenId").build());
        WeixinResponse weixinResponse = service.sendMessageToAllByOpenId(token, templateMessage);

        System.out.println("send message result : " + weixinResponse.getErrmsg());
    }

    @Test
    public void sendMessageToAllUserByTagTest() {
        String token = "51_0okx4MiFi4yN3wY6wCLOo04GVD9M3w48tVa4Nm-cL6AzRQKJ4aOD_n7FwzJV9IrgJUO0i6HxrGIoAq2MOfiEkOBz2gkj3RxsCoUH6udC_dB10yQFjXHpv7p7hOmg6WzaOTa0ooF0CP-kBwOWDHBgABAZDX";
        ToAllMessage toAllMessage = new ToAllMessage();
        toAllMessage.setFilter(FilterData.builder().is_to_all(true).tag_id(2).build());
        toAllMessage.setMsgtype("text");
        toAllMessage.setText(CustomerMessageContent.builder().content("test send message by tag").build());
        WeixinResponse weixinResponse = service.sendMessageToAllByTag(token, toAllMessage);

        System.out.println("send message result : " + weixinResponse.getErrmsg());
    }

    @Test
    public void sendMessageToUserTest() {
        String token = "51_mDrkpkh24i0n-BV5EvTc5YBadlUaQhLvfjB3UUyNnxt9FvUKUz0_zvsj5QZCXKWOCr30--hDW5KTjN3pG0x8P2nL1x6OvVLpb0jFcqNooUb67q1IXoSPMe3-RNWvx8IKOlXkky-zsU-ovyNBIYEbABAQGM";
        CustomerMessage templateMessage = new CustomerMessage();
        templateMessage.setTouser("oHgeM50LShy2Hh3H1arsvqsUpNM0");
        templateMessage.setMsgtype("text");
        templateMessage.setText(CustomerMessageContent.builder().content("test send message to user").build());
        WeixinResponse weixinResponse = service.sendMessageToUser(token, templateMessage);

        System.out.println("send message result : " + weixinResponse.getErrmsg());
    }


}
