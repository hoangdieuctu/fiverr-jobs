package com.raunheim.los.controller;

import com.raunheim.los.dto.response.ResponseDto;
import com.raunheim.los.service.BinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bins", produces = MediaType.APPLICATION_JSON_VALUE)
public class BinController {

    private final BinService binService;

    @ResponseBody
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto uploadBins(@RequestParam("file") MultipartFile file) {
        log.info("Upload bins");
        this.binService.processUploadBins(file);
        return ResponseDto.success();
    }

}
