package com.api.dev.model;

import lombok.Data;

import java.util.Map;

@Data
public class WeChatTemplateMessage {

    private String touser;

    private String template_id;

    private Map<String, TemplateData> data;
}
