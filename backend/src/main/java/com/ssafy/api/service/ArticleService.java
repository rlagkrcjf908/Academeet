package com.ssafy.api.service;

import com.ssafy.api.request.ArticlePostReq;
import com.ssafy.db.entity.Article;

import java.util.List;

public interface ArticleService {
    boolean writeArticle(int groupId, int userId, ArticlePostReq articlePostReq);

    List<Article> listArticle(int groupId, int userId);

    Article getArticle(int articleno);

    boolean updateArticle(int articleno,ArticlePostReq articlePostReq);

    boolean deleteArticle(int articleno);
}
