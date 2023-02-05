package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel("MeetCreatePostRequest")
public class MeetCreateReq {
    @ApiModelProperty(name="title", example="ssafy_web")
    @JsonProperty("title")
    String title;
    @ApiModelProperty(name="date")
    @JsonProperty("date")
    Date date;
    @ApiModelProperty(name="starttime")
    @JsonProperty("starttime")
    Time starttime;
    @ApiModelProperty(name="endtime")
    @JsonProperty("endtime")
    Time endtime;

    @ApiModelProperty(name="groupid")
    @JsonProperty("groupid")
    int groupid;

    @ApiModelProperty(name="users")
    @JsonProperty("users")
    List<Integer> users;
}
