package com.raunheim.los.service;

import com.raunheim.los.dto.request.article.Articles;
import com.raunheim.los.model.Article;
import com.raunheim.los.model.ArticleUnit;
import com.raunheim.los.repository.ArticleRepository;
import com.raunheim.los.repository.ArticleUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.Date;

@Slf4j
@Service
public class ArticleService extends LosService<Articles> {

    @Value("${storage.location.articles}")
    private String storageLocation;

    private final ClientService clientService;

    private final ArticleRepository articleRepository;
    private final ArticleUnitRepository articleUnitRepository;

    public ArticleService(FileProcessingService fileProcessingService,
                          FileStorageService fileStorageService,
                          Validator validator,
                          ClientService clientService,
                          ArticleRepository articleRepository,
                          ArticleUnitRepository articleUnitRepository) {
        super(Articles.class, validator, fileProcessingService, fileStorageService);
        this.clientService = clientService;
        this.articleRepository = articleRepository;
        this.articleUnitRepository = articleUnitRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processUploadArticles(MultipartFile file) {
        var articles = super.storeToFile(file);
        log.info("Stored articles into file: {}", articles);

        articles.getArticle().forEach(this::saveArticle);
    }

    public Article getOrCreateDummyArticle(String articleId, String unit) {
        var existingArticle = articleRepository.findByArticleId(articleId);
        if (existingArticle != null) {
            return existingArticle;
        }

        var newArticle = new Article();
        newArticle.setDescription("Dummy article");
        newArticle.setArticleId(articleId);
        newArticle.setCreationDate(new Date());
        newArticle.setLastChangeDate(new Date());
        articleRepository.save(newArticle);
        log.info("Saved new dummy article: {}", newArticle);

        var articleUnit = getOrCreateDummyArticleUnit(newArticle.getId(), unit);
        newArticle.setUnitId(articleUnit.getId());
        articleRepository.save(newArticle);
        return newArticle;
    }

    private ArticleUnit getOrCreateDummyArticleUnit(Long articleId, String unit) {
        var existingArticleUnit = articleUnitRepository.findByArticleIdAndUnit(articleId, unit);
        if (existingArticleUnit != null) {
            return existingArticleUnit;
        }

        var newArticleUnit = new ArticleUnit();
        newArticleUnit.setArticleId(articleId);
        newArticleUnit.setUnit(unit);
        articleUnitRepository.save(newArticleUnit);
        log.info("Saved dummy article unit: {}", newArticleUnit);
        return newArticleUnit;
    }

    private void saveArticle(com.raunheim.los.dto.request.article.Article article) {
        var existing = articleRepository.findByArticleId(article.getTZIDEN());
        if (existing == null) {
            existing = Article.from(article);
            log.info("Saved article: {}", existing);
        } else {
            existing.copy(article);
            log.info("Updated article: {}", existing);
        }

        var client = clientService.getOrCreate(article.getClient());
        existing.setClient(client.getId());
        articleRepository.save(existing);

        var unit0 = getOrCreateArticleUnit(article, 0, existing.getId());
        log.info("Article unit 0: {}", unit0);

        var unit1 = getOrCreateArticleUnit(article, 1, existing.getId());
        log.info("Article unit 1: {}", unit1);

        var unit2 = getOrCreateArticleUnit(article, 2, existing.getId());
        log.info("Article unit 2: {}", unit2);

        existing.setUnitId(unit0.getId());
        articleRepository.save(existing);
    }

    private ArticleUnit getOrCreateArticleUnit(com.raunheim.los.dto.request.article.Article article, int unit, Long articleId) {
        var articleUnit = articleUnitRepository.findByArticleIdAndUnit(articleId, article.getUnitId(unit));
        if (articleUnit != null) {
            log.info("Found article unit: {}", articleUnit.getId());
            articleUnit.copy(article, unit);
            articleUnitRepository.save(articleUnit);
            log.info("Updated article unit: {}", articleUnit);
            return articleUnit;
        }
        log.info("Create new article unit");
        var newArticleUnit = ArticleUnit.from(article, unit);
        newArticleUnit.setArticleId(articleId);
        return articleUnitRepository.save(newArticleUnit);
    }
}
