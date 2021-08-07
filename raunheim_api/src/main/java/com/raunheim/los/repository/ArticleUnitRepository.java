package com.raunheim.los.repository;

import com.raunheim.los.model.ArticleUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleUnitRepository extends JpaRepository<ArticleUnit, Long> {

    ArticleUnit findByArticleIdAndUnit(Long articleId, String unit);

}
