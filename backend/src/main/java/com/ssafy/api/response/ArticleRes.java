package com.ssafy.api.response;

import com.ssafy.db.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("ArticleResponse")
public class ArticleRes {
    @ApiModelProperty(name="articleTitle")
    String title;
    @ApiModelProperty(name="articleContent")
    String content;
    @ApiModelProperty(name="articleFile")
    int userId;

    @ApiModelProperty(name="articleDate")
    Date date;

    public static ArticleRes of(Article article){

        ArticleRes res = new ArticleRes();
        res.setTitle(article.getTitle());
        res.setUserId(article.getUserid().getId());
        res.setContent(article.getContent());
        res.setDate(article.getDate());
        return res;
    }


}
