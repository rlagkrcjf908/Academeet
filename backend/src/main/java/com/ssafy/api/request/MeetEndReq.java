package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@ApiModel("MeetEndPostRequest")
public class MeetEndReq {

    @ApiModelProperty(name="chat")
    @JsonProperty("chat")
    String chat;
    @ApiModelProperty(name="stt")
    @JsonProperty("stt")
    String stt;
    @ApiModelProperty(name="video")
    @JsonProperty("video")
    String video;
    @ApiModelProperty(name="endtime")
    @JsonProperty("endtime")
    LocalTime endtime;
}
