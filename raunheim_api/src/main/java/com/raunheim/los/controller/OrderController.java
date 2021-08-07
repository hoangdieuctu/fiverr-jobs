package com.raunheim.los.controller;

import com.raunheim.los.dto.response.ResponseDto;
import com.raunheim.los.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;
    private final DeleteOrderService deleteOrderService;
    private final PickOrderService pickOrderService;
    private final PackOrderService packOrderService;
    private final OrderReservationService orderReservationService;
    private final DeleteOrderReservationService deleteOrderReservationService;
    private final ReleaseOrderReservationService releaseOrderReservationService;

    @ResponseBody
    @PostMapping(value = "/newOrders", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto uploadOrder(@RequestParam("file") MultipartFile file) {
        log.info("Upload order");
        this.orderService.processUploadOrder(file);
        return ResponseDto.success();
    }

    @ResponseBody
    @PostMapping(value = "/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto deleteOrder(@RequestParam("file") MultipartFile file) {
        log.info("Delete order");
        this.deleteOrderService.processDeleteOrder(file);
        return ResponseDto.success();
    }

    @ResponseBody
    @PostMapping(value = "/releases", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto releaseReservation(@RequestParam("file") MultipartFile file) {
        log.info("Release order reservation");
        this.releaseOrderReservationService.processReleaseOrderReservation(file);
        return ResponseDto.success();
    }

    @ResponseBody
    @PostMapping(value = "/picks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto pickOrder(@RequestParam("file") MultipartFile file) {
        log.info("Pick order");
        this.pickOrderService.processUploadPickOrder(file);
        return ResponseDto.success();
    }

    @ResponseBody
    @PostMapping(value = "/packs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto packOrder(@RequestParam("file") MultipartFile file) {
        log.info("Pack order");
        this.packOrderService.processUploadPackOrder(file);
        return ResponseDto.success();
    }

    @ResponseBody
    @PostMapping(value = "/reservations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto createReservation(@RequestParam("file") MultipartFile file) {
        log.info("Create order reservation");
        this.orderReservationService.processUploadOrderReservation(file);
        return ResponseDto.success();
    }

    @ResponseBody
    @PostMapping(value = "/reservations/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto deleteReservation(@RequestParam("file") MultipartFile file) {
        log.info("Delete order reservation");
        this.deleteOrderReservationService.processDeleteOrderReservation(file);
        return ResponseDto.success();
    }


}
