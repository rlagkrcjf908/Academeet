package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findArticleByGroupidAndUserid(int groupId, int userId);

    Article findArticleById(int articleno);

    Article findArticleByTitle(String title);

    void deleteArticleById(int articleno);

}
