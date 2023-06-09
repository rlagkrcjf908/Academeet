package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findArticleByGroupid(Group group);

    Article findArticleById(int articleno);

    Article findArticleByTitle(String title);

    @Transactional
    void deleteArticleById(int articleno);

}
