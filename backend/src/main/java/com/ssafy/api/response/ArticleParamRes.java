package com.ssafy.api.response;

import com.ssafy.db.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("ArticleParameterResponse")
public class ArticleParamRes {
    @ApiModelProperty(name="articleList")
    List<Article> articleList;

    public static ArticleParamRes of(List<Article> articles){
        ArticleParamRes res = new ArticleParamRes();
        res.setArticleList(articles);
        return res;
    }
}
