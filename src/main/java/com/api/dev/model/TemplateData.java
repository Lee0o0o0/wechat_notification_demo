package com.api.dev.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateData {
    private String value;
    private String color;
}
