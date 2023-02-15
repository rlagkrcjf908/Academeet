package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AttendPostRequest")
public class AttendReq {
    @ApiModelProperty(name="userId", example="User_Id")
    @JsonProperty("userId")
    String userId;//사용자 이름으로 변경할 수도 있습니다.
    @ApiModelProperty(name="attendcount", example="attendance")
    @JsonProperty("attendcount")
    int attendcount;
}
