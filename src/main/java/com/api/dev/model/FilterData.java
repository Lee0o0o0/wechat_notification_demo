package com.api.dev.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterData {
    private boolean is_to_all;
    private Integer tag_id;
}
