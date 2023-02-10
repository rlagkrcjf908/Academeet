package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findArticleByGroupidAndUserid(Group group, User user);

    Article findArticleById(int articleno);

    Article findArticleByTitle(String title);

    void deleteArticleById(int articleno);

}
