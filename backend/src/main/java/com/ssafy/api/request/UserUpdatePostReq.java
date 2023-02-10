package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserUpdatePostRequest")
public class UserUpdatePostReq {
    @ApiModelProperty(name="email", example="ssafy_web")
    @JsonProperty("email")
    String email;
    @ApiModelProperty(name="name", example="your_name")
    @JsonProperty("name")
    String name;
    @ApiModelProperty(name="birth", example="your_birth")
    @JsonProperty("birth")
    String birth;
    @ApiModelProperty(name="nick", example="your_nick")
    @JsonProperty("nick")
    String nick;
    @ApiModelProperty(name="phone", example="your_phone")
    @JsonProperty("phone")
    String phone;
//    @ApiModelProperty(name="profile", example="your_profile")
//    @JsonProperty("profile")
//    MultipartFile profile;
}
