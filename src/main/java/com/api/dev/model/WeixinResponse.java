package com.api.dev.model;

import lombok.Data;

@Data
public class WeixinResponse {
    private String msgid;
    private int errcode;
    private String errmsg;
}
