package com.raunheim.los.repository;

import com.raunheim.los.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByArticleId(String articleId);

}
