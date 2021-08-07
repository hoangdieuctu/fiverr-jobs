package com.raunheim.los.service;

import com.raunheim.los.dto.request.pick.Pick;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.model.OrderStatus;
import com.raunheim.los.model.Process;
import com.raunheim.los.model.ProcessEntity;
import com.raunheim.los.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;

@Slf4j
@Service
public class PickOrderService extends LosService<Pick> {

    private static final Integer DEFAULT_PICK_TYPE_ID = 1200;
    private static final Integer DEFAULT_PROCESS_ORDER_ID = 1;
    private static final Integer DEFAULT_PROCESS_ORDER_LINE_ID = 2;

    @Value("${storage.location.orders.pick}")
    private String storageLocation;

    private final BinService binService;
    private final EmployeeService employeeService;
    private final ArticleService articleService;

    private final ArticleUnitRepository articleUnitRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProcessRepository processRepository;
    private final ProcessEntityRepository processEntityRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public PickOrderService(FileProcessingService fileProcessingService,
                            FileStorageService fileStorageService,
                            Validator validator,
                            BinService binService,
                            ArticleService articleService,
                            EmployeeService employeeService,
                            ArticleUnitRepository articleUnitRepository,
                            OrderRepository orderRepository,
                            OrderLineRepository orderLineRepository,
                            ProcessRepository processRepository,
                            ProcessEntityRepository processEntityRepository,
                            OrderLineReservationRepository orderLineReservationRepository) {
        super(Pick.class, validator, fileProcessingService, fileStorageService);
        this.binService = binService;
        this.employeeService = employeeService;
        this.articleService = articleService;
        this.articleUnitRepository = articleUnitRepository;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.processRepository = processRepository;
        this.processEntityRepository = processEntityRepository;
        this.orderLineReservationRepository = orderLineReservationRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processUploadPickOrder(MultipartFile file) {
        var pick = super.storeToFile(file);
        log.info("Stored pick order into file: {}", pick);

        pickOrder(pick);
    }

    private void pickOrder(Pick pick) {
        var order = orderRepository.findByOrderId(pick.getTPAPN());

        if (order == null) {
            throw new LosException(String.format("Pick %s of order %s does not exists",
                    pick.getTPAPPO(), pick.getTPAPN()));
        }

        var orderLine = orderLineRepository.findByOrderIdAndLine(order.getId(), pick.getTPAPPO());
        if (orderLine == null) {
            throw new LosException(String.format("Pick %s of order %s does not exists by order line %s not found",
                    pick.getTPAPPO(), pick.getTPAPN(), pick.getTPAPPO()));
        }

        var article = articleService.getOrCreateDummyArticle(pick.getTPIDEN(), pick.getTPME());

        var articleUnit = articleUnitRepository.findByArticleIdAndUnit(article.getId(), pick.getTPME());
        if (articleUnit == null) {
            throw new LosException(String.format("Pick %s of order %s does not exists by article unit %s not found",
                    pick.getTPAPPO(), pick.getTPAPN(), pick.getTPME()));
        }

        if (!orderLine.getArticleId().equals(article.getId())) {
            throw new LosException("Reject by article not match with order line");
        }

        var locationEnd = binService.getOrCreate(pick.getTPPPLZ());
        var locationStart = binService.getOrCreate(pick.getLocationStartId());
        var employee = employeeService.getOrCreate(pick.getTPUSER());

        var newProcess = Process.from(pick);
        newProcess.setArticle(article.getId());
        newProcess.setUnit(articleUnit.getId());
        newProcess.setLocationEnd(locationEnd.getId());
        newProcess.setLocationStart(locationStart.getId());
        newProcess.setEmployee(employee.getId());
        newProcess.setProcessType(DEFAULT_PICK_TYPE_ID);
        newProcess.setProcessId(String.format("%s-%s-%s", pick.getTPAPN(), pick.getTPPALN(), locationStart.getId()));

        processRepository.save(newProcess);
        log.info("Saved process: {}", newProcess);

        changeReservationStatus(orderLine.getId(), pick.getTPNRKS(), OrderStatus.BEFORE_PACKING);
        log.info("Updated order line reservation status of order line {} and pick list {} to {}",
                orderLine.getId(), pick.getTPNRKS(), OrderStatus.BEFORE_PACKING);

        saveProcessEntity(order.getId(), newProcess.getId(), DEFAULT_PROCESS_ORDER_ID);
        saveProcessEntity(orderLine.getId(), newProcess.getId(), DEFAULT_PROCESS_ORDER_LINE_ID);
    }

    private void saveProcessEntity(Long entityId, Long processId, Integer type) {
        var newProcessEntity = new ProcessEntity();
        newProcessEntity.setEntity(entityId);
        newProcessEntity.setType(type);
        newProcessEntity.setProcess(processId);
        processEntityRepository.save(newProcessEntity);
        log.info("Saved process entity: {}", newProcessEntity);
    }

    private void changeReservationStatus(Long orderLineId, String pickList, OrderStatus status) {
        var reservations = orderLineReservationRepository.findByOrderLineIdAndPickList(orderLineId, pickList);
        if (reservations == null || reservations.isEmpty()) {
            throw new LosException("Order line reservation does not exists");
        }
        reservations.forEach(re -> {
            re.setStatus(status);
            orderLineReservationRepository.save(re);
        });
    }
}
