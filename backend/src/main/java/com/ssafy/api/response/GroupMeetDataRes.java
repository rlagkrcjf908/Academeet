package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("GroupMeetDataResponse")
public class GroupMeetDataRes {
    @ApiModelProperty(name="articleTitle")
    String title;
    @ApiModelProperty(name="articleContent")
    String stt;
    @ApiModelProperty(name="articleFile")
    String video;
    @ApiModelProperty(name="articleDate")
    Date date;
}
