package com.raunheim.los.repository;

import com.raunheim.los.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByOrderId(Long orderId);

    OrderLine findByOrderIdAndLine(Long orderId, Integer line);
}
