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
    int userId;//사용자 이름으로 변경할 수도 있습니다.
    @ApiModelProperty(name="attendance", example="attendance")
    @JsonProperty("attendance")
    int attendcount;
}
