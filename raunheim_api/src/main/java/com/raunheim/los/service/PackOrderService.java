package com.raunheim.los.service;

import com.raunheim.los.dto.request.pack.Pack;
import com.raunheim.los.dto.request.pack.PackLine;
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
public class PackOrderService extends LosService<Pack> {

    private static final Integer DEFAULT_PACK_TYPE_ID = 1300;
    private static final Integer DEFAULT_PROCESS_ORDER_ID = 1;
    private static final Integer DEFAULT_PROCESS_ORDER_LINE_ID = 2;

    @Value("${storage.location.orders.pack}")
    private String storageLocation;

    private final BinService binService;
    private final EmployeeService employeeService;
    private final ArticleService articleService;

    private final ArticleUnitRepository articleUnitRepository;
    private final OrderRepository orderRepository;
    private final OrderPropertyRepository orderPropertyRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProcessRepository processRepository;
    private final ProcessEntityRepository processEntityRepository;
    private final OrderLineReservationRepository orderLineReservationRepository;

    public PackOrderService(FileProcessingService fileProcessingService,
                            FileStorageService fileStorageService,
                            Validator validator,
                            BinService binService,
                            ArticleService articleService,
                            EmployeeService employeeService,
                            ArticleUnitRepository articleUnitRepository,
                            OrderRepository orderRepository,
                            OrderPropertyRepository orderPropertyRepository,
                            OrderLineRepository orderLineRepository,
                            ProcessRepository processRepository,
                            ProcessEntityRepository processEntityRepository,
                            OrderLineReservationRepository orderLineReservationRepository) {
        super(Pack.class, validator, fileProcessingService, fileStorageService);
        this.binService = binService;
        this.employeeService = employeeService;
        this.articleService = articleService;
        this.articleUnitRepository = articleUnitRepository;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.orderPropertyRepository = orderPropertyRepository;
        this.processRepository = processRepository;
        this.processEntityRepository = processEntityRepository;
        this.orderLineReservationRepository = orderLineReservationRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processUploadPackOrder(MultipartFile file) {
        var pack = super.storeToFile(file);
        log.info("Stored pack order into file: {}", pack);

        pack.getPackageLines().getPackageLine().forEach(line -> packOrder(pack, line));
    }

    private void packOrder(Pack pack, PackLine line) {
        var order = orderRepository.findByOrderId(pack.getPKAPN());
        if (order == null) {
            throw new LosException(String.format("Orderline %s of order %s does not exists",
                    line.getPKAPPO(), pack.getPKAPN()));
        }

        var orderProperty = orderPropertyRepository.findById(order.getId()).orElse(null);
        if (orderProperty == null) {
            throw new LosException(String.format("Order property not found for order %s", pack.getPKAPN()));
        }

        var orderLine = orderLineRepository.findByOrderIdAndLine(order.getId(), line.getPKAPPO());
        if (orderLine == null) {
            throw new LosException(String.format("Orderline %s of order %s does not exists",
                    line.getPKAPPO(), pack.getPKAPN()));
        }

        var article = articleService.getOrCreateDummyArticle(line.getPKIDEN(), line.getPKME());

        var articleUnit = articleUnitRepository.findByArticleIdAndUnit(article.getId(), line.getPKME());
        if (articleUnit == null) {
            throw new LosException(String.format("Orderline %s of order %s does not exists by article unit %s not found",
                    line.getPKAPPO(), pack.getPKAPN(), line.getPKME()));
        }

        if (!orderLine.getArticleId().equals(article.getId())) {
            throw new LosException("Reject by article not match with order line");
        }

        var employee = employeeService.getOrCreate(line.getPKUSER());

        var newProcess = Process.from(pack, line);
        newProcess.setArticle(article.getId());
        newProcess.setUnit(articleUnit.getId());
        newProcess.setEmployee(employee.getId());
        newProcess.setProcessType(DEFAULT_PACK_TYPE_ID);
        newProcess.setProcessId(String.format("%s-%s", pack.getPKPKNR(), line.getPKLFDN()));

        processRepository.save(newProcess);
        log.info("Saved process: {}", newProcess);

        changeReservationStatus(orderLine.getId(), OrderStatus.AFTER_PACKING);
        log.info("Updated order line reservation status of order line {} to {}",
                orderLine.getId(), OrderStatus.AFTER_PACKING);

        orderProperty.setPackStation(pack.getPKPPLA());
        orderPropertyRepository.save(orderProperty);
        log.info("Updated pack station for order: {}", pack.getPKAPN());

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

    private void changeReservationStatus(Long orderLineId, OrderStatus status) {
        var reservations = orderLineReservationRepository.findByOrderLineId(orderLineId);
        if (reservations == null || reservations.isEmpty()) {
            throw new LosException("Order line reservation does not exists");
        }
        reservations.forEach(re -> {
            re.setStatus(status);
            orderLineReservationRepository.save(re);
        });
    }
}
