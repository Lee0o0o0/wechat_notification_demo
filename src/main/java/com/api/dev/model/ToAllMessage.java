package com.api.dev.model;

import lombok.Data;

@Data
public class ToAllMessage {

    private FilterData filter;
    private String msgtype;
    private CustomerMessageContent text;

}
