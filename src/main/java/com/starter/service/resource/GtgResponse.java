package com.starter.service.resource;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GtgResponse {
    public static final String OK = "ok";
    public static final String NOT_OK = "not_ok";

    private final String mode;
    private final String status;
    private final String version;

    public GtgResponse(String mode, String status, String version) {
        this.mode = mode;
        this.status = status;
        this.version = version;
    }
}
