package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("UserMeetResponse")
public class UserMeetRes {
    @ApiModelProperty(name="Meet Id")
    int meetId;
    @ApiModelProperty(name="Group Title")
    String groupTitle;
    @ApiModelProperty(name="Meet Title")
    String meetTitle;
    @ApiModelProperty(name="Start Time")
    Date startTime;
    @ApiModelProperty(name="End Time")
    Date endTime;

}
