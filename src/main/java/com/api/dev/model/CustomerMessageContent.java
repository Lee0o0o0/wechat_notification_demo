package com.api.dev.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerMessageContent {
    private String content;
}
