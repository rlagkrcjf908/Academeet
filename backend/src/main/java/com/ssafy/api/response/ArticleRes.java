package com.ssafy.api.response;

import com.ssafy.db.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ArticleResponse")
public class ArticleRes {
    @ApiModelProperty(name="articleTitle")
    String title;
    @ApiModelProperty(name="articleContent")
    String content;
    @ApiModelProperty(name="articleFile")
    String file;

    public static ArticleRes of(Article article){
        ArticleRes res = new ArticleRes();
        res.setTitle(article.getTitle());
        res.setFile(article.getContent());
        res.setContent(article.getContent());
        return res;
    }


}
