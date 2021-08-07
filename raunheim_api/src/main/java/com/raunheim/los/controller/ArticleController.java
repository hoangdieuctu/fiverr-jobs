package com.raunheim.los.controller;

import com.raunheim.los.dto.response.ResponseDto;
import com.raunheim.los.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    private final ArticleService articleService;

    @ResponseBody
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto uploadArticles(@RequestParam("file") MultipartFile file) {
        log.info("Upload articles");
        this.articleService.processUploadArticles(file);
        return ResponseDto.success();
    }

}
