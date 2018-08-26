package com.example.token.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author cailei.lu
 * @description
 * @date 2018/8/3
 */

@Data
@Builder
public class ResponseTemplate {

    public Integer code;

    public String message;

    public Object data;

}
