package com.az.ms.dao;

import com.az.ms.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Igor_Azarny on 11/13/2017.
 */
@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {

}
