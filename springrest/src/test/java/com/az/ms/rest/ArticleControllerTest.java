package com.az.ms.rest;

import com.az.ms.Application;
import com.az.ms.misc.JsonUtil;
import com.az.ms.model.Article;
import com.az.ms.service.ArticleService;
import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


/**
 * Created by Igor_Azarny on 11/14/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public class ArticleControllerTest {

    @Value("http://localhost:${local.server.port}/api")
    private String serviceURL;

    @Autowired
    private ArticleService articleService;

    @Test
    public void testGetAllArticles() throws Exception {

        Article persistedArticle = articleService.createArticle(
                new Article("Hi", "There one more time!"));

        String id = given()
                .when()
                .get(serviceURL + "/articles").prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(1))
                .body("[0].title", equalTo("Hi"))
                .body("[0].content", equalTo("There one more time!"))
                .extract().path("[0].id").toString();

        articleService.deleteArticle(Long.parseLong(id));


    }

    @Test
    public void testCreateArticle() throws Exception {

        Article art = new Article("Hi", "HAL9000");

        String id = given()
                .when()
                .contentType(ContentType.JSON)
                .body(JsonUtil.toJSON(art))
                .post(serviceURL + "/articles").prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("Hi"))
                .body("content", equalTo("HAL9000"))
                .extract().path("id").toString();

        articleService.deleteArticle(Long.parseLong(id));


    }

    @Test
    public void testGetArticleById() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Halo", "Better world"));

        given()
                .when()
                .pathParam("id", persistedArticle.getId())
                .get(serviceURL + "/articles/{id}").prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("Halo"))
                .body("content", equalTo("Better world"));

        articleService.deleteArticle(persistedArticle.getId());

    }

    @Test
    public void testUpdateArticle() throws Exception {
        Article persistedArticle = articleService.createArticle(
                new Article("Halo", "Better world"));



        persistedArticle.setContent("More better world");
        persistedArticle.setLink("No such place");


        persistedArticle = articleService.updateArticle(
                persistedArticle.getId(), persistedArticle);

        given()
                .when()
                .contentType(ContentType.JSON)
                .pathParam("id", persistedArticle.getId())
                .body(JsonUtil.toJSON(persistedArticle))
                .put(serviceURL + "/articles/{id}").prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("Halo"))
                .body("content", equalTo("More better world"))
                .body("link", equalTo("No such place"));

        articleService.deleteArticle(persistedArticle.getId());

    }
}