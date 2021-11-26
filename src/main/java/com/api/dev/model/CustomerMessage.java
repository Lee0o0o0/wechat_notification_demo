package com.api.dev.model;

import lombok.Data;

@Data
public class CustomerMessage {

    private Object touser;
    private String msgtype;
    private CustomerMessageContent text;

}
