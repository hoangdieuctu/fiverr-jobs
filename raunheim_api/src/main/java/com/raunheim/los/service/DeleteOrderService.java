package com.raunheim.los.service;

import com.raunheim.los.dto.request.order.DeleteOrder;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.model.OrderStatus;
import com.raunheim.los.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.List;

@Slf4j
@Service
public class DeleteOrderService extends LosService<DeleteOrder> {

    @Value("${storage.location.orders.delete}")
    private String storageLocation;

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final WhOrderLinePropertyRepository whOrderLinePropertyRepository;
    private final OrderPropertyRepository orderPropertyRepository;
    private final OrderLinePropertyRepository orderLinePropertyRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public DeleteOrderService(FileProcessingService fileProcessingService,
                              FileStorageService fileStorageService,
                              Validator validator,
                              OrderRepository orderRepository,
                              OrderLineRepository orderLineRepository,
                              WhOrderLinePropertyRepository whOrderLinePropertyRepository,
                              OrderPropertyRepository orderPropertyRepository,
                              OrderLinePropertyRepository orderLinePropertyRepository,
                              OrderLineReservationRepository orderLineReservationRepository) {
        super(DeleteOrder.class, validator, fileProcessingService, fileStorageService);
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.whOrderLinePropertyRepository = whOrderLinePropertyRepository;
        this.orderPropertyRepository = orderPropertyRepository;
        this.orderLinePropertyRepository = orderLinePropertyRepository;
        this.orderLineReservationRepository = orderLineReservationRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processDeleteOrder(MultipartFile file) {
        var order = super.storeToFile(file);
        log.info("Stored delete order into file: {}", order);

        var orderId = order.getASAPN();
        var existingOrder = orderRepository.findByOrderId(orderId);
        if (existingOrder == null) {
            throw new LosException(String.format("Order %s does not exists", orderId));
        }

        var orderProperty = orderPropertyRepository.findById(existingOrder.getId()).orElse(null);
        if (orderProperty == null) {
            throw new LosException(String.format("Order property %s does not exists", orderId));
        }

        var allowedStatuses = List.of(OrderStatus.NEW, OrderStatus.UNRESERVED,
                OrderStatus.RESERVATION_REQUESTED, OrderStatus.RESERVED, OrderStatus.OPEN_REQUESTED);
        if (allowedStatuses.contains(orderProperty.getStatus())) {
            deleteOrderLineAndReservations(existingOrder.getId());
            log.info("Deleted all order lines and reservations for order: {}", existingOrder.getId());

            orderProperty.setStatus(OrderStatus.DELETED);
            orderPropertyRepository.save(orderProperty);
            log.info("Updated order property status to DELETED");
        } else {
            throw new LosException(String.format("Order %s has status %s and cannot be deleted anymore",
                    orderId, orderProperty.getStatus().name().toLowerCase()));
        }
    }

    private void deleteOrderLineAndReservations(Long orderId) {
        var orderLines = orderLineRepository.findByOrderId(orderId);
        if (orderLines != null) {
            orderLines.forEach(orderLine -> {
                deleteOrderLineReservation(orderLine.getId());
                deleteOrderLine(orderLine.getId());
            });
        }
    }

    private void deleteOrderLine(Long orderLineId) {
        orderLinePropertyRepository.deleteById(orderLineId);
        whOrderLinePropertyRepository.deleteById(orderLineId);
        orderLineRepository.deleteById(orderLineId);
    }

    private void deleteOrderLineReservation(Long orderLineId) {
        var reservations = orderLineReservationRepository.findByOrderLineId(orderLineId);
        reservations.forEach(reservation -> orderLineReservationRepository.deleteById(reservation.getId()));
    }
}
