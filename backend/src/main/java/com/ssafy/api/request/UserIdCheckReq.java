package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserIdCheckRequest")
public class UserIdCheckReq {
    @ApiModelProperty(name="email", example="ssafy_web")
    @JsonProperty("email")
    String email;
}
