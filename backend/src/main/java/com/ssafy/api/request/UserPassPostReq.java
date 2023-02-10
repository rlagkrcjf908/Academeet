package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserPassPostRequest")
public class UserPassPostReq {
    @ApiModelProperty(name="password")
    @JsonProperty("password")
    String password;
}
