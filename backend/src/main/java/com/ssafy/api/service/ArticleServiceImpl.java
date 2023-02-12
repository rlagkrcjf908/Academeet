package com.ssafy.api.service;

import com.ssafy.api.request.ArticlePostReq;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
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
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean writeArticle(int groupId, int userId, ArticlePostReq articlePostReq) {
        if(articlePostReq.getTitle()==null||articlePostReq.getContent()==null){
            return false;
        }
        Group group = groupRepository.findGroupById(groupId);
        User user = userRepository.findUserById(userId);
        Article article  = new Article();
        article.setTitle(articlePostReq.getTitle());
        article.setContent(articlePostReq.getContent());
        article.setDate(articlePostReq.getDate());
        article.setGroupid(group);
        article.setUserid(user);
        articleRepository.save(article);
        return true;
    }

    @Override
    public List<Article> listArticle(int groupId, int userId) {
        Group group = groupRepository.findGroupById(groupId);
        User user = userRepository.findUserById(userId);
        List<Article> articles = articleRepository.findArticleByGroupid(group);
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
        articleRepository.save(article);
        return true;

    }

    @Override
    public boolean deleteArticle(int articleno) {
        Article article = articleRepository.findArticleById(articleno);
        if (article ==null) return false;
        articleRepository.deleteById(articleno);
        return true;
    }
}
