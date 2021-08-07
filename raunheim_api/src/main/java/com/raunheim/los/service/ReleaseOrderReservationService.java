package com.raunheim.los.service;

import com.raunheim.los.dto.request.reservation.ReleaseOrderReservation;
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
public class ReleaseOrderReservationService extends LosService<ReleaseOrderReservation> {

    @Value("${storage.location.reservations.release}")
    private String storageLocation;

    private final BinService binService;
    private final ArticleService articleService;

    private final ArticleUnitRepository articleUnitRepository;
    private final OrderRepository orderRepository;
    private final OrderPropertyRepository orderPropertyRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderLinePropertyRepository orderLinePropertyRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public ReleaseOrderReservationService(FileProcessingService fileProcessingService,
                                          FileStorageService fileStorageService,
                                          Validator validator,
                                          BinService binService,
                                          ArticleService articleService,
                                          ArticleUnitRepository articleUnitRepository,
                                          OrderRepository orderRepository,
                                          OrderPropertyRepository orderPropertyRepository,
                                          OrderLineRepository orderLineRepository,
                                          OrderLinePropertyRepository orderLinePropertyRepository,
                                          OrderLineReservationRepository orderLineReservationRepository) {
        super(ReleaseOrderReservation.class, validator, fileProcessingService, fileStorageService);
        this.binService = binService;
        this.articleService = articleService;
        this.articleUnitRepository = articleUnitRepository;
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
    public void processReleaseOrderReservation(MultipartFile file) {
        var releaseOrderReservation = super.storeToFile(file);
        log.info("Stored release order reservation into file: {}", releaseOrderReservation);

        releaseOrderReservation(releaseOrderReservation);
    }

    private void releaseOrderReservation(ReleaseOrderReservation release) {
        var order = orderRepository.findByOrderId(release.getTPAPN());
        if (order == null) {
            throw new LosException(String.format("Order %s does not exists", release.getTPAPN()));
        }

        var orderProperty = orderPropertyRepository.findById(order.getId()).orElse(null);
        if (orderProperty == null) {
            throw new LosException(String.format("Order property %s does not exists", order.getId()));
        }

        if (OrderStatus.RESERVED.equals(orderProperty.getStatus())
                || OrderStatus.RESERVATION_REQUESTED.equals(orderProperty.getStatus())) {
            deleteOrderReservation(order.getId());
            log.info("Delete all order lines reservations for order: {}", order.getId());

            saveOrderLineReservation(order.getId(), release);

            order.setPriority(release.getTPMDE3());
            order.setShippingDeadline(release.getShippingDeadline());
            orderRepository.save(order);
            log.info("Updated order: {}", order.getId());

            orderProperty.setStatus(OrderStatus.OPEN);
            orderPropertyRepository.save(orderProperty);
            log.info("Updated order property [{}] to: {}", order.getId(), OrderStatus.OPEN);
        } else {
            throw new LosException(String.format("Order %s is not in status %s",
                    release.getTPAPN(), OrderStatus.RESERVED.name().toLowerCase()));
        }
    }

    private void saveOrderLineReservation(Long orderId, ReleaseOrderReservation release) {
        var reservations = release.getOrderlineReservations();
        reservations.getOrderlineReservation().forEach(reservation -> {
            var article = articleService.getOrCreateDummyArticle(reservation.getTPIDEN(), reservation.getTPME());

            var unit = articleUnitRepository.findByArticleIdAndUnit(article.getId(), reservation.getTPME());
            if (unit == null) {
                throw new LosException(String.format("Article unit %s does not exists", reservation.getTPME()));
            }

            var orderLine = orderLineRepository.findByOrderIdAndLine(orderId, reservation.getTPAPPO());
            if (orderLine == null) {
                throw new LosException(String.format("Order line %s does not exist", reservation.getTPAPPO()));
            }

            var location = binService.getOrCreate(reservation.getLocationId());

            var newReservation = com.raunheim.los.model.OrderLineReservation.from(reservation);
            newReservation.setTransportOrderId(release.getTPPALN());
            newReservation.setPickType(release.getTPTTYP());
            newReservation.setPickLocation(location.getId());
            newReservation.setUnit(unit.getId());
            newReservation.setOrderLineId(orderLine.getId());
            newReservation.setStatus(OrderStatus.OPEN);

            orderLineReservationRepository.save(newReservation);
            log.info("Saved order line reservation: {}", newReservation);

            updateOrderLineStatus(orderLine.getId(), OrderStatus.OPEN);
            log.info("Update order line {} status to {}", orderLine.getId(), OrderStatus.OPEN);
        });
    }

    private void deleteOrderReservation(Long orderId) {
        var orderLines = orderLineRepository.findByOrderId(orderId);
        if (orderLines != null) {
            orderLines.forEach(orderLine -> deleteOrderLineReservation(orderLine.getId()));
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
