package com.hust.accountcommon.util.apitemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicInput {
    private String ver;

    private String id;

    private long time;

    private Long receiveTime;

    private int nonce;

    private String token;

    private String sign;

}
