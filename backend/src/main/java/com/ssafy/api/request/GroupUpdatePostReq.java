package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("GroupCreatePostRequest")
public class GroupUpdatePostReq {
    @ApiModelProperty(name="name", example="group_name")
    @JsonProperty("name")
    String name;

    //유저의 값 받아오기
    @JsonProperty("add")
    List<Integer> addUsers;

    @JsonProperty("del")
    List<Integer> delUsers;

}
