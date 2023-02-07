package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AttendanceGroupResponse")
public class AttendGroupRes {
    @ApiModelProperty(name="userId")
    int userId;
    @ApiModelProperty(name="name")
    String name;
    @ApiModelProperty(name="allAtt")
    double allAtt;
    @ApiModelProperty(name="profile")
    String profile;
}
