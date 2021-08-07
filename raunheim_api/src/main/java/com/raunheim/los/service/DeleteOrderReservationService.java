package com.raunheim.los.service;

import com.raunheim.los.dto.request.reservation.DeleteOrderReservation;
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
public class DeleteOrderReservationService extends LosService<DeleteOrderReservation> {

    @Value("${storage.location.reservations.delete}")
    private String storageLocation;

    private final OrderRepository orderRepository;
    private final OrderPropertyRepository orderPropertyRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinePropertyRepository orderLinePropertyRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public DeleteOrderReservationService(FileProcessingService fileProcessingService,
                                         FileStorageService fileStorageService,
                                         Validator validator,
                                         OrderRepository orderRepository,
                                         OrderPropertyRepository orderPropertyRepository,
                                         OrderLineRepository orderLineRepository,
                                         OrderLinePropertyRepository orderLinePropertyRepository,
                                         OrderLineReservationRepository orderLineReservationRepository) {
        super(DeleteOrderReservation.class, validator, fileProcessingService, fileStorageService);
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
    public void processDeleteOrderReservation(MultipartFile file) {
        var deleteReservation = super.storeToFile(file);
        log.info("Stored delete order reservation into file: {}", deleteReservation);

        deleteOrderReservation(deleteReservation.getASAPN());
    }

    private void deleteOrderReservation(String orderId) {
        var order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new LosException(String.format("Order %s does not exists", orderId));
        }

        var orderProperty = orderPropertyRepository.findById(order.getId()).orElse(null);
        if (orderProperty == null) {
            throw new LosException(String.format("Order property %s does not exists", order.getId()));
        }

        if (OrderStatus.RESERVED.equals(orderProperty.getStatus())
                || OrderStatus.OPEN_REQUESTED.equals(orderProperty.getStatus())) {
            deleteOrderReservation(order.getId());
            log.info("Delete all order lines reservations for order: {}", order.getId());

            orderProperty.setStatus(OrderStatus.UNRESERVED);
            orderPropertyRepository.save(orderProperty);
            log.info("Updated order property [{}] to: {}", order.getId(), OrderStatus.UNRESERVED);
        } else {
            throw new LosException(String.format("Reservation of order %s cannot be deleted, as is order status = %s",
                    orderId, orderProperty.getStatus().name().toLowerCase()));
        }
    }

    private void deleteOrderReservation(Long orderId) {
        var orderLines = orderLineRepository.findByOrderId(orderId);
        if (orderLines != null) {
            orderLines.forEach(orderLine -> {
                deleteOrderLineReservation(orderLine.getId());
                updateOrderLineStatus(orderLine.getId(), OrderStatus.UNRESERVED);
            });
        }
    }

    private void updateOrderLineStatus(Long orderLineId, OrderStatus status) {
        var property = orderLinePropertyRepository.findById(orderLineId).orElse(null);
        if (property == null) {
            throw new LosException(String.format("Order line property %s does not exists", orderLineId));
        }

        property.setStatus(status);
        orderLinePropertyRepository.save(property);
    }

    private void deleteOrderLineReservation(Long orderLineId) {
        var reservations = orderLineReservationRepository.findByOrderLineId(orderLineId);
        reservations.forEach(reservation -> orderLineReservationRepository.deleteById(reservation.getId()));
    }
}
