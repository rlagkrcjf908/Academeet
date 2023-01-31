package com.ssafy.api.service;

import com.ssafy.api.request.ArticlePostReq;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.GroupRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private GroupRepositorySupport groupRepositorySupport;

    @Autowired
    private  UserRepositorySupport userRepositorySupport;

    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public boolean writeArticle(int groupId, int userId, ArticlePostReq articlePostReq) {
        if(articlePostReq.getTitle()==null||articlePostReq.getContent()==null){
            return false;
        }
        Group group = groupRepositorySupport.findGroupById(groupId).get();
        User user = userRepositorySupport.findUserById(userId).get();
        Article article  = new Article();
        article.setTitle(articlePostReq.getTitle());
        article.setContent(articlePostReq.getContent());
        article.setFile(articlePostReq.getFile());
        article.setGroupid(group);
        article.setUserid(user);
        articleRepository.save(article);
        return true;
    }

    @Override
    public List<Article> listArticle(int groupId, int userId) {
        List<Article> articles = articleRepository.findArticleByGroupidAndUserid(groupId,userId);
        return articles;
    }

    @Override
    public Article getArticle(int articleno) {
        Article article = articleRepository.findArticleById(articleno);
        return article;
    }

    @Override
    public boolean updateArticle(int articleno,ArticlePostReq articlePostReq) {
        Article article = articleRepository.findArticleById(articleno);
        if(article == null) return false;
        article.setTitle(articlePostReq.getTitle());
        article.setContent(articlePostReq.getContent());
        article.setFile(articlePostReq.getFile());
        articleRepository.save(article);
        return true;

    }

    @Override
    public boolean deleteArticle(int articleno) {
        Article article = articleRepository.findArticleById(articleno);
        if (article ==null) return false;
        articleRepository.deleteArticleById(articleno);
        return true;
    }
}
