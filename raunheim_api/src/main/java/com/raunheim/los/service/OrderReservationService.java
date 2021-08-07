package com.raunheim.los.service;

import com.raunheim.los.dto.request.reservation.OrderLineReservation;
import com.raunheim.los.dto.request.reservation.OrderLinesReservation;
import com.raunheim.los.dto.request.reservation.OrderReservation;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.model.OrderStatus;
import com.raunheim.los.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;

@Slf4j
@Service
public class OrderReservationService extends LosService<OrderReservation> {

    @Value("${storage.location.reservations.create}")
    private String storageLocation;

    private final BinService binService;

    private final OrderRepository orderRepository;
    private final OrderPropertyRepository orderPropertyRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinePropertyRepository orderLinePropertyRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public OrderReservationService(FileProcessingService fileProcessingService,
                                   FileStorageService fileStorageService,
                                   Validator validator,
                                   BinService binService,
                                   OrderRepository orderRepository,
                                   OrderPropertyRepository orderPropertyRepository,
                                   OrderLineRepository orderLineRepository,
                                   OrderLinePropertyRepository orderLinePropertyRepository,
                                   OrderLineReservationRepository orderLineReservationRepository) {
        super(OrderReservation.class, validator, fileProcessingService, fileStorageService);
        this.binService = binService;
        this.orderRepository = orderRepository;
        this.orderPropertyRepository = orderPropertyRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderLinePropertyRepository = orderLinePropertyRepository;
        this.orderLineReservationRepository = orderLineReservationRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processUploadOrderReservation(MultipartFile file) {
        var reservation = super.storeToFile(file);
        log.info("Stored order reservation into file: {}", reservation);

        saveOrderLineReservations(reservation.getREAPN(), reservation.getOrderlines());
    }

    private void saveOrderLineReservations(String orderId, OrderLinesReservation orderLinesReservation) {
        var order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new LosException(String.format("Order %s does not exists", orderId));
        }

        var orderProperty = orderPropertyRepository.findById(order.getId()).orElse(null);
        if (orderProperty == null) {
            throw new LosException(String.format("Order property %s does not exists", order.getId()));
        }

        if (OrderStatus.UNRESERVED.equals(orderProperty.getStatus())
                || OrderStatus.RESERVATION_REQUESTED.equals(orderProperty.getStatus())) {
            deleteOrderReservation(order.getId());
            log.info("Delete all order lines reservations for order: {}", order.getId());

            orderLinesReservation.getOrderline().forEach(line -> saveOrderLineReservation(order.getId(), line));

            orderProperty.setStatus(OrderStatus.RESERVED);
            orderPropertyRepository.save(orderProperty);
            log.info("Updated order property [{}] to: {}", order.getId(), OrderStatus.RESERVED);
        } else {
            throw new LosException(String.format("Reservation for order %s with order status = %s failed",
                    orderId, orderProperty.getStatus().name().toLowerCase()));
        }
    }

    private void deleteOrderReservation(Long orderId) {
        var orderLines = orderLineRepository.findByOrderId(orderId);
        if (orderLines != null) {
            orderLines.forEach(orderLine -> {
                var reservations = orderLineReservationRepository.findByOrderLineId(orderLine.getId());
                reservations.forEach(reservation -> orderLineReservationRepository.deleteById(reservation.getId()));
            });
        }
    }

    private void saveOrderLineReservation(Long orderId, OrderLineReservation orderLineReservation) {
        var location = binService.getOrCreate(orderLineReservation.getLocationId());
        var orderLine = orderLineRepository.findByOrderIdAndLine(orderId, orderLineReservation.getREAPPO());
        if (orderLine == null) {
            throw new LosException(String.format("Order line %s does not exists", orderId));
        }

        var reservation = com.raunheim.los.model.OrderLineReservation.from(orderLineReservation);
        reservation.setPickLocation(location.getId());
        reservation.setUnit(orderLine.getUnit());
        reservation.setOrderLineId(orderLine.getId());
        reservation.setStatus(OrderStatus.RESERVED);
        orderLineReservationRepository.save(reservation);
        log.info("Saved order line reservation: {}", reservation);

        updateOrderLineStatus(orderLine.getId(), OrderStatus.RESERVED);
        log.info("Updated order line property [{}] to: {}", orderLine.getId(), OrderStatus.RESERVED);
    }

    private void updateOrderLineStatus(Long orderLineId, OrderStatus status) {
        var property = orderLinePropertyRepository.findById(orderLineId).orElse(null);
        if (property == null) {
            throw new LosException(String.format("Order line property %s does not exists", orderLineId));
        }

        property.setStatus(status);
        orderLinePropertyRepository.save(property);
    }

}
