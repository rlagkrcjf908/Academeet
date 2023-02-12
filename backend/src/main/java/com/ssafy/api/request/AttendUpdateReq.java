package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AttendUpdateRequest")
public class AttendUpdateReq {
    @ApiModelProperty(name="meetId", example="Meet_Id")
    @JsonProperty("meetId")
    int meetId;
    @ApiModelProperty(name="attendance", example="attendance")
    @JsonProperty("attendance")
    double attendance;
}
