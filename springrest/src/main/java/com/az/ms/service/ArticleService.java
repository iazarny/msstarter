package com.az.ms.service;

import com.az.ms.model.Article;

import java.util.List;

/**
 * Created by Igor_Azarny on 11/13/2017.
 */
public interface ArticleService {

     List<Article> getAllArticles();

     Article createArticle(Article article) ;

     Article getArticleById(Long articleId);

     Article updateArticle(Long articleId, Article artDetail);

     Article deleteArticle(Long articleId);
}
