package com.az.ms.rest;

import com.az.ms.model.Article;
import com.az.ms.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Igor_Azarny on 11/13/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@Api("Article CRUD REST endpoint")
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;




    @GetMapping("/articles")
    @ApiOperation("Get all articles")
    public List<Article> getAllArticles() {
        log.info("Get all articles");
        return articleService.getAllArticles();
    }

    @PostMapping("/articles")
    @ApiOperation("Create an article")
    public Article createArticle(@Valid @RequestBody Article article) {
        log.info("Create article with title {}", article.getTitle());
        return articleService.createArticle(article);
    }

    @GetMapping("/articles/{id}")
    @ApiOperation("Get article with specific ID")
    public ResponseEntity<Article> getArticleById(@PathVariable(value = "id") Long articleId) {
        log.info("Get article by id {}", articleId);
        Article article = articleService.getArticleById(articleId);
        if(article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(article);
    }

    @PutMapping("/articles/{id}")
    @ApiOperation("Update article with specific ID")
    public ResponseEntity<Article> updateArticle(@PathVariable(value = "id") Long articleId,
                                           @Valid @RequestBody Article articleDetail) {
        log.info("Update article by id {}", articleId);
        Article article = articleService.updateArticle(articleId, articleDetail);
        if(article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/articles/{id}")
    @ApiOperation("Delete article by ID")
    public ResponseEntity<Article> deleteArticle(@PathVariable(value = "id") Long artId) {
        log.info("Delete article by id {}", artId);
        Article article = articleService.deleteArticle(artId);
        if(article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }
}
