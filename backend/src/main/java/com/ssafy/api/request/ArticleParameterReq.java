package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.db.entity.Article;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("ArticleParameterRequest")

public class ArticleParameterReq {
    @JsonProperty("articles")
    List<Article> articles;

}
