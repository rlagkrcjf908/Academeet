package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@ApiModel("UserMeetResponse")
public class UserMeetRes {
    @ApiModelProperty(name="meetid")
    int meetId;
    @ApiModelProperty(name="grouptitle")
    String groupTitle;
    @ApiModelProperty(name="meettitle")
    String meetTitle;
    @ApiModelProperty(name = "date")
    Date date;
    @ApiModelProperty(name="starttime")
    Time startTime;
    @ApiModelProperty(name="endtime")
    Time endTime;

}
