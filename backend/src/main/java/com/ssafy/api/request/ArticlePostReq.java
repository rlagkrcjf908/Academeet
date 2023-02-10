package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ArticlePostRequest")
public class ArticlePostReq {

    @ApiModelProperty(name="title", example="article_title")
    @JsonProperty("title")
    String title;
    @ApiModelProperty(name="content", example="article_content")
    @JsonProperty("content")
    String content;
    @ApiModelProperty(name="file", example="file")
    @JsonProperty("file")
    String file;

}
