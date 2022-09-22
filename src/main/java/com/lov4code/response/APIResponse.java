package com.lov4code.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class APIResponse {
    private Long id;
    private String timestamp;
    private String message;
    private String path;
    private String status;
    private int statusCode;
    private boolean success;
}
