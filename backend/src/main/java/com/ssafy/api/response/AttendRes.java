package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("AttendanceResponse")
public class AttendRes {
    @ApiModelProperty(name="meetid")
    int meetId;
    @ApiModelProperty(name="title")
    String title;
    @ApiModelProperty(name="MeetDate")
    Date date;
    @ApiModelProperty(name="articleFile")
    double attendance;



}
