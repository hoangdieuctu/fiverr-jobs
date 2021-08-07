package com.raunheim.los.controller;

import com.raunheim.los.dto.response.ResponseDto;
import com.raunheim.los.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockController {

    private final StockService stockService;

    @ResponseBody
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto uploadStocks(@RequestParam("file") MultipartFile file) {
        log.info("Upload stocks");
        this.stockService.processUploadStocks(file);
        return ResponseDto.success();
    }

}
