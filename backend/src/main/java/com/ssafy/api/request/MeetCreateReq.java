package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel("MeetCreatePostRequest")
public class MeetCreateReq {
    @ApiModelProperty(name="title", example="ssafy_web")
    @JsonProperty("title")
    String title;
    @ApiModelProperty(name="Date")
    @JsonProperty("Date")
    Date date;
    @ApiModelProperty(name="starttime")
    @JsonProperty("starttime")
    LocalTime starttime = LocalTime.now();
    @ApiModelProperty(name="endtime")
    @JsonProperty("endtime")
    LocalTime endtime;

    List<Group> groups;

    List<User> users;
}
