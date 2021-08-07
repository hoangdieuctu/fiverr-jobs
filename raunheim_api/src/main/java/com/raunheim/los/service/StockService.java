package com.raunheim.los.service;

import com.raunheim.los.dto.request.stock.Stock;
import com.raunheim.los.dto.request.stock.Stocks;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.model.Inventory;
import com.raunheim.los.repository.ArticleUnitRepository;
import com.raunheim.los.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;

@Slf4j
@Service
public class StockService extends LosService<Stocks> {

    @Value("${storage.location.stocks}")
    private String storageLocation;

    private final ClientService clientService;
    private final BinService binService;
    private final ArticleService articleService;

    private final InventoryRepository inventoryRepository;
    private final ArticleUnitRepository articleUnitRepository;

    public StockService(FileProcessingService fileProcessingService,
                        FileStorageService fileStorageService,
                        Validator validator,
                        ClientService clientService,
                        BinService binService,
                        ArticleService articleService,
                        InventoryRepository inventoryRepository,
                        ArticleUnitRepository articleUnitRepository) {
        super(Stocks.class, validator, fileProcessingService, fileStorageService);
        this.clientService = clientService;
        this.binService = binService;
        this.articleService = articleService;
        this.inventoryRepository = inventoryRepository;
        this.articleUnitRepository = articleUnitRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processUploadStocks(MultipartFile file) {
        var stocks = super.storeToFile(file);
        log.info("Stored stocks into file: {}", stocks);

        stocks.getStock().forEach(this::saveStock);
    }

    private void saveStock(Stock stock) {
        var article = articleService.getOrCreateDummyArticle(stock.getBMIDEN(), stock.getBMME());

        var unit = articleUnitRepository.findByArticleIdAndUnit(article.getId(), stock.getBMME());
        if (unit == null) {
            throw new LosException(String.format("Article unit %s does not exists", stock.getBMIDEN()));
        }

        var location = binService.getOrCreate(stock.getStorageLocation());

        var existing = inventoryRepository.findByStorageLocation(location.getId());
        if (existing == null) {
            existing = Inventory.from(stock);
            existing.setArticle(article.getId());
            existing.setUnit(unit.getId());
            existing.setStorageLocation(location.getId());
        } else {
            existing.copy(stock);
        }

        var client = clientService.getOrCreate(stock.getClient());
        existing.setClient(client.getId());
        inventoryRepository.save(existing);
        log.info("Saved inventory: {}", existing);
    }

}
