package com.raunheim.los.service;

import com.raunheim.los.dto.request.order.Order;
import com.raunheim.los.dto.request.order.OrderLine;
import com.raunheim.los.dto.request.order.OrderLines;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.model.*;
import com.raunheim.los.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;

@Slf4j
@Service
public class OrderService extends LosService<Order> {

    @Value("${storage.location.orders.create}")
    private String storageLocation;

    private final ClientService clientService;
    private final OrderTypeService orderTypeService;
    private final ArticleService articleService;

    private final ArticleUnitRepository articleUnitRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final WhOrderPropertyRepository whOrderPropertyRepository;
    private final WhOrderLinePropertyRepository whOrderLinePropertyRepository;
    private final OrderPropertyRepository orderPropertyRepository;
    private final OrderLinePropertyRepository orderLinePropertyRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public OrderService(FileProcessingService fileProcessingService,
                        FileStorageService fileStorageService,
                        Validator validator,
                        ClientService clientService,
                        OrderTypeService orderTypeService,
                        ArticleService articleService,
                        ArticleUnitRepository articleUnitRepository,
                        OrderRepository orderRepository,
                        OrderLineRepository orderLineRepository,
                        WhOrderPropertyRepository whOrderPropertyRepository,
                        WhOrderLinePropertyRepository whOrderLinePropertyRepository,
                        OrderPropertyRepository orderPropertyRepository,
                        OrderLinePropertyRepository orderLinePropertyRepository,
                        OrderLineReservationRepository orderLineReservationRepository) {
        super(Order.class, validator, fileProcessingService, fileStorageService);
        this.clientService = clientService;
        this.orderTypeService = orderTypeService;
        this.articleService = articleService;
        this.articleUnitRepository = articleUnitRepository;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.whOrderPropertyRepository = whOrderPropertyRepository;
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
    public void processUploadOrder(MultipartFile file) {
        var order = super.storeToFile(file);
        log.info("Stored order into file: {}", order);

        var orderId = order.getAKAPN();
        var existingOrder = orderRepository.findByOrderId(orderId);
        if (existingOrder == null) {
            log.info("Order [{}] does not exist, save new order", orderId);
            saveOrOverrideOrder(order, null);
        } else {
            var existingOrderProperty = orderPropertyRepository.findById(existingOrder.getId()).orElse(null);
            if (existingOrderProperty == null) {
                throw new LosException(String.format("Order property %s does not exists", orderId));
            }
            if (OrderStatus.DELETED.equals(existingOrderProperty.getStatus())) {
                log.info("Order status is deleted, clean up data");
                deleteOrder(existingOrder.getId());

                log.info("Override the order");
                saveOrOverrideOrder(order, existingOrder.getId());
            } else {
                var message = String.format("New order %s already exists with status = %s", order.getAKAPN(), existingOrderProperty.getStatus().name().toLowerCase());
                throw new LosException(message);
            }
        }
    }

    private void deleteOrder(Long orderId) {
        deleteOrderLines(orderId);

        whOrderPropertyRepository.deleteById(orderId);
        orderPropertyRepository.deleteById(orderId);
        log.info("Deleted order properties");

        flush();
    }

    private void deleteOrderLines(Long orderId) {
        var orderLines = orderLineRepository.findByOrderId(orderId);
        if (orderLines != null) {
            orderLines.forEach(orderLine -> deleteOrderLine(orderLine.getId()));
            log.info("Deleted {} order lines", orderLines.size());
        }
    }

    private void deleteOrderLine(Long orderLineId) {
        deleteOrderReservations(orderLineId);

        orderLinePropertyRepository.deleteById(orderLineId);
        whOrderLinePropertyRepository.deleteById(orderLineId);
        orderLineRepository.deleteById(orderLineId);
    }

    private void deleteOrderReservations(Long orderLineId) {
        var reservations = orderLineReservationRepository.findByOrderLineId(orderLineId);
        reservations.forEach(reservation -> orderLineReservationRepository.deleteById(reservation.getId()));
        log.info("Deleted {} order line reservation for: {}", reservations.size(), orderLineId);
    }

    private void saveOrOverrideOrder(Order order, Long overrideOrderId) {
        var client = clientService.getOrCreate(order.getClient());
        var type = orderTypeService.getOrCreate(order.getAKAART());

        var newOrder = com.raunheim.los.model.Order.from(order);
        newOrder.setClient(client.getId());
        newOrder.setType(type.getId());
        newOrder.setId(overrideOrderId);
        orderRepository.save(newOrder);
        log.info("Saved order: {}", newOrder);

        saveNewWhOrderProperty(order, newOrder.getId());
        saveNewOrderProperty(newOrder.getId());
        saveNewOrderLines(order.getOrderlines(), newOrder.getId());
    }

    private void saveNewOrderProperty(Long newOrderId) {
        var orderProperty = new OrderProperty();
        orderProperty.setId(newOrderId);
        orderProperty.setStatus(OrderStatus.NEW);
        orderPropertyRepository.save(orderProperty);
        log.info("Saved order property: {}", orderProperty);
    }

    private void saveNewWhOrderProperty(Order order, Long newOrderId) {
        var whOrderProperty = WhOrderProperty.from(order);
        whOrderProperty.setId(newOrderId);
        whOrderPropertyRepository.save(whOrderProperty);
        log.info("Saved wh order property: {}", whOrderProperty);
    }

    private void saveNewOrderLines(OrderLines orderLine, Long orderId) {
        orderLine.getOrderline().forEach(line -> saveNewOrderLine(line, orderId));
    }

    private void saveNewOrderLine(OrderLine orderLine, Long orderId) {
        var article = articleService.getOrCreateDummyArticle(orderLine.getAPIDEN(), orderLine.getAPME());

        var unit = articleUnitRepository.findByArticleIdAndUnit(article.getId(), orderLine.getAPME());
        if (unit == null) {
            throw new LosException(String.format("Article unit %s does not exists", orderLine.getAPIDEN()));
        }

        var newOrderLine = com.raunheim.los.model.OrderLine.from(orderLine);
        newOrderLine.setArticleId(article.getId());
        newOrderLine.setUnit(unit.getId());
        newOrderLine.setOrderId(orderId);
        orderLineRepository.save(newOrderLine);
        log.info("Saved order line: {}", newOrderLine);

        saveNewWhOrderLineProperty(orderLine, newOrderLine.getId());
        saveNewOrderLineProperty(newOrderLine.getId());
    }

    private void saveNewOrderLineProperty(Long newOrderLineId) {
        var orderLineProperty = new OrderLineProperty();
        orderLineProperty.setId(newOrderLineId);
        orderLineProperty.setStatus(OrderStatus.NEW);
        orderLinePropertyRepository.save(orderLineProperty);
        log.info("Saved order line property: {}", orderLineProperty);
    }

    private void saveNewWhOrderLineProperty(OrderLine orderLine, Long newOrderLineId) {
        var whOrderLineProperty = WhOrderLineProperty.from(orderLine);
        whOrderLineProperty.setId(newOrderLineId);
        whOrderLinePropertyRepository.save(whOrderLineProperty);
        log.info("Saved wh order line property: {}", whOrderLineProperty);
    }

    private void flush() {
        orderLinePropertyRepository.flush();
        whOrderLinePropertyRepository.flush();
        orderLineRepository.flush();

        whOrderPropertyRepository.flush();
        orderPropertyRepository.flush();
        orderRepository.flush();

        orderLineReservationRepository.flush();
    }

}
