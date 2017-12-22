package com.az.ms.service;

import com.az.ms.Application;
import com.az.ms.model.Article;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Igor_Azarny on 11/14/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles({"test"})
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGetAllArticles() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Hi", "There one more time!")
        );
        Assert.assertFalse(articleService.getAllArticles().isEmpty());
    }

    @org.junit.Test
    public void testCreateArticle() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Hi", "There !")
        );
        Assert.assertTrue(persistedArticle.getId() > 0);
    }

    @org.junit.Test
    public void testGetArticleById() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Hi", "There one more time!")
        );
        Article retreivedArticle = articleService.getArticleById(persistedArticle.getId());
        Assert.assertNotNull(retreivedArticle);
    }

    @org.junit.Test
    public void testUpdateArticle() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Hi", "There one more time!")
        );
        Article retreivedArticle = articleService.getArticleById(persistedArticle.getId());
        retreivedArticle.setTitle("XYZ");
        retreivedArticle.setContent("ASD");
        Article updated = articleService.updateArticle(retreivedArticle.getId(), retreivedArticle);
        Assert.assertEquals("XYZ", updated.getTitle());
        Assert.assertEquals("ASD", updated.getContent());
    }

    @org.junit.Test
    public void testDeleteArticle() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Hi", "There one more time!")
        );

        articleService.deleteArticle(persistedArticle.getId());

        Article retreivedArticle = articleService.getArticleById(persistedArticle.getId());

        Assert.assertNull(retreivedArticle);
    }
}