package com.youruan.dentistry.core.base.aliyunsms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSmsResponse {

    @JsonProperty("Message") private String message;
    @JsonProperty("RequestId") private String requestId;
    @JsonProperty("Code") private String code;
    @JsonProperty("BizId") private String bizId;

}
