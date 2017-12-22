package com.az.ms.service;

import com.az.ms.dao.ArticleDao;
import com.az.ms.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Igor_Azarny on 11/13/2017.
 */
@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public List<Article> getAllArticles() {
        return articleDao.findAll();
    }

    public Article getArticleById(Long articleId) {
        return articleDao.findById(articleId).orElse(null);
    }

    @Transactional
    public Article createArticle(Article article) {
        return articleDao.save(article);
    }

    @Transactional
    public Article updateArticle(Long articleId, Article artDetail) {
        Article article = getArticleById(articleId);
        if (article != null) {
            article.setTitle(artDetail.getTitle());
            article.setContent(artDetail.getContent());
            article.setLink(artDetail.getLink());
            article = articleDao.save(article);
        }
        return article;
    }

    @Transactional
    public Article deleteArticle(Long articleId) {
        Article article = getArticleById(articleId);
        if (article != null) {
            articleDao.deleteById(articleId);
        }
        return article;
    }


}
