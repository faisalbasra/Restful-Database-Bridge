package com.akaiteam.web;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

@Data
public class Result {
    private long rowCount = -1;
    @JsonRawValue
    private String data;
    private long queryDuration = -1;
}
